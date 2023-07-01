package kr.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;
import kr.util.DBUtil;

public class OrderDAO {
	//싱글턴 패턴
		private static OrderDAO instance = 
				        new OrderDAO();
		public static OrderDAO getInstance() {
			return instance;
		}
		private OrderDAO() {}
		
		// 주문 등록/
		public void insertOrder(OrderVO order, 
				List<OrderDetailVO> orderDetailList)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			PreparedStatement pstmt4 = null;
			PreparedStatement pstmt5 = null;
			ResultSet rs = null;
			String sql = null;
			int order_num = 0;
			
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				// 주문번호 구하기
				sql = "SELECT orders_seq.nextval FROM dual";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					order_num = rs.getInt(1);
				}
				
				// 주문 정보 저장
				sql = "INSERT INTO orders (order_num, item_name, order_status, order_total, order_payment, "
						+ "order_quantity, order_name, order_zipcode, order_address1, order_address2, "
						+ "mem_phone, order_notice, mem_num) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, order_num);
				pstmt2.setString(2, order.getItem_name());
				pstmt2.setInt(3, order.getOrder_status());
				pstmt2.setInt(4, order.getOrder_total());
				pstmt2.setInt(5, order.getOrder_payment());
				pstmt2.setInt(6, order.getOrder_quantity());
				pstmt2.setString(7, order.getOrder_name());
				pstmt2.setString(8, order.getOrder_zipcode());
				pstmt2.setString(9, order.getOrder_address1());
				pstmt2.setString(10, order.getOrder_address2());
				pstmt2.setString(11, order.getMem_phone());
				pstmt2.setString(12, order.getOrder_notice());
				pstmt2.setInt(13, order.getMem_num());
				
				pstmt2.executeUpdate();
				
				// 주문 상세 정보 저장
				sql = "INSERT INTO orders_detail (detail_num, item_num, "
						+ "item_name, item_price, item_total, order_quantity, mem_num, "
						+ "order_num) VALUES (orders_detail_seq.nextval,?,?,?,?,?,?,?)";
				pstmt3 = conn.prepareStatement(sql);
				
				for(int i=0; i<orderDetailList.size(); i++) {
					OrderDetailVO orderDetail = orderDetailList.get(i);
					
					// ?에 데이터 바인딩
					pstmt3.setInt(1, orderDetail.getItem_num());
					pstmt3.setString(2, orderDetail.getItem_name());
					pstmt3.setInt(3, orderDetail.getItem_price());
					pstmt3.setInt(4, orderDetail.getItem_total());
					pstmt3.setInt(5, orderDetail.getOrder_quantity());
					pstmt3.setInt(6, orderDetail.getMem_num());
					pstmt3.setInt(7, order_num);
					
					pstmt3.addBatch(); // 쿼리를 메모리에 올림
					
					// 계속 추가하면 outOfMemory 발생, 1000개 단위로 executeBatch()
					if(i % 1000 == 0) {
						pstmt3.executeBatch();
					}
				} // end of for
				pstmt3.executeBatch();
				
				// 상품의 재고수 차감
				sql = "UPDATE item SET item_stock=item_stock-? WHERE item_num=?";
				pstmt4 = conn.prepareStatement(sql);
				
				for(int i=0; i<orderDetailList.size(); i++) {
					OrderDetailVO orderDetail = orderDetailList.get(i);
					pstmt4.setInt(1, orderDetail.getOrder_quantity());
					pstmt4.setInt(2, orderDetail.getItem_num());
					pstmt4.addBatch();
					
					if(i % 1000 == 0) {
						pstmt4.executeBatch();
					}
				} // end of for
				pstmt4.executeBatch();
				
				// 장바구니에서 주문 상품 삭제
				sql = "DELETE FROM cart WHERE mem_num=?";
				pstmt5 = conn.prepareStatement(sql);
				pstmt5.setInt(1, order.getMem_num());
				pstmt5.executeUpdate();
				
				conn.commit();
				
			} catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt5, null);
				DBUtil.executeClose(null, pstmt4, null);
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
		}
		// 관리자 - 전체 주문 개수, 검색 주문 개수/
		public int getOrderCount(String keyfield, String keyword)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				conn = DBUtil.getConnection();
				
				if(keyword != null 
						&& !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "WHERE order_num = ?";
					else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE ?";
					else if(keyfield.equals("3")) sub_sql += "WHERE item_name LIKE ?";
				}
				
				sql = "SELECT COUNT(*) FROM orders o JOIN omember m ON o.mem_num = m.mem_num" + sub_sql;
				pstmt = conn.prepareStatement(sql);
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) {
						pstmt.setString(1, keyword);
					}else {
						pstmt.setString(1,"%"+keyword+"%");
					}
				}
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return count;
		}
		
		// 관리자 - 전체 주문 목록, 검색 주문 목록/
		public List<OrderVO> getListOrder(int start, int end, String keyfield, String keyword)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				conn = DBUtil.getConnection();
				
				if(keyword != null 
						&& !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "WHERE order_num = ?";
					else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE ?";
					else if(keyfield.equals("3")) sub_sql += "WHERE item_name LIKE ?";
				}
				
				sql = "SELECT * FROM "
						+ "(SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM orders o JOIN omember m "
						+ "ON o.mem_num = m.mem_num" + sub_sql + 
						" ORDER BY order_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
				pstmt = conn.prepareStatement(sql);
				
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) {
						pstmt.setString(++cnt, keyword);
					} else {
						pstmt.setString(++cnt, "%" + keyword + "%");
					}
				}
				
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<OrderVO>();
				while(rs.next()) {
					OrderVO order = new OrderVO();
					order.setOrder_num(rs.getInt("order_num"));
					order.setItem_name(rs.getString("item_name"));
						order.setOrder_total(rs.getInt("order_total"));
						order.setOrder_payment(rs.getInt("order_payment"));
						order.setOrder_status(rs.getInt("order_status"));
						order.setOrder_name(rs.getString("order_name"));
						order.setOrder_zipcode(rs.getString("order_zipcode"));
						order.setOrder_address1(rs.getString("order_address1"));
						order.setOrder_address2(rs.getString("order_address2"));
						order.setMem_phone(rs.getString("mem_phone"));
						order.setOrder_notice(rs.getString("order_notice"));
						order.setOrder_regdate(rs.getDate("order_regdate"));
						order.setMem_num(rs.getInt("mem_num"));
						
						list.add(order);
				}
				
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		// 사용자 - 전체 주문 개수, 검색 주문 개수
		public int getOrderCountByMem_num(String keyfield, String keyword, int mem_num)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sub_sql = "";
			String sql = null;
			int count = 0;
			
			try {
				conn = DBUtil.getConnection();
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "AND order_num = ?";
					else if(keyfield.equals("2")) sub_sql += "AND item_name LIKE ?";
				}
				sql = "SELECT COUNT(*) FROM orders WHERE mem_num = ?" + sub_sql;
				pstmt = conn.prepareStatement(sql);
				// ?에 데이터 바인딩
				pstmt.setInt(1, mem_num);
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) {
						pstmt.setString(2, keyword);
					} else if(keyfield.equals("2")){
						pstmt.setString(2, "%" + keyword + "%");
					}
				}
				rs = pstmt.executeQuery();
				if(rs.next()) { 
					count = rs.getInt(1);
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return count;
		}
		
		// 사용자 - 전체 주문 목록, 검색 주문 목록
		public List<OrderVO> getListOrderByMem_num(int start, int end, String keyfield, 
				String keyword, int mem_num)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderVO> list = null;
			String sub_sql = "";
			String sql = null;
			int cnt = 0;
			
			try {
				conn = DBUtil.getConnection();
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql += "AND order_num=?";
					else if(keyfield.equals("2")) sub_sql += "AND item_name LIKE ?";
				}
				sql = "SELECT * FROM (SELECT a.*, rownum rnum "
						+ "FROM (SELECT * FROM orders WHERE mem_num=? " + sub_sql 
						+ "ORDER BY order_num DESC)a) WHERE rnum>=? AND rnum<=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(++cnt, mem_num);
				if(keyword != null && !"".equals(keyword)) {
					if(keyfield.equals("1")) {
						pstmt.setString(++cnt, keyword);
					} else if(keyfield.equals("2")){
						pstmt.setString(++cnt, "%" + keyword + "%");
					}
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<OrderVO>();
				
				while(rs.next()) {
					OrderVO order = new OrderVO();
					order.setOrder_num(rs.getInt("order_num"));
					order.setItem_name(rs.getString("item_name"));
					order.setOrder_total(rs.getInt("order_total"));
					order.setOrder_payment(rs.getInt("order_payment"));
					order.setOrder_status(rs.getInt("order_status"));
					order.setOrder_name(rs.getString("order_name"));
					order.setOrder_zipcode(rs.getString("order_zipcode"));
					order.setOrder_address1(rs.getString("order_address1"));
					order.setOrder_address2(rs.getString("order_address2"));
					order.setMem_phone(rs.getString("mem_phone"));
					order.setOrder_notice(rs.getString("order_notice"));
					order.setOrder_regdate(rs.getDate("order_regdate"));
					order.setMem_num(rs.getInt("mem_num"));
					
					list.add(order);
				}
				
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		
		// 개별 상품 목록/
		public List<OrderDetailVO>getListOrderDetail(int order_num)throws Exception{
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OrderDetailVO> list = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM orders_detail WHERE order_num=? ORDER BY item_num DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, order_num);
				rs = pstmt.executeQuery();
				list = new ArrayList<OrderDetailVO>();
				
				while(rs.next()) {
					OrderDetailVO detail = new OrderDetailVO();
					detail.setItem_num(rs.getInt("item_num"));
					detail.setItem_name(rs.getString("item_name"));
					detail.setItem_price(rs.getInt("item_price"));
					detail.setItem_total(rs.getInt("item_total"));
					detail.setOrder_quantity(rs.getInt("order_quantity"));
					detail.setOrder_num(rs.getInt("order_num"));
					detail.setMem_num(rs.getInt("mem_num"));
					
					list.add(detail);
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
		
		// 주문 삭제(삭제 시 재고를 원상 복귀시키지 않음, 주문 취소일 때 원상 복귀)/
		public void deleteOrder(int order_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				sql = "DELETE FROM orders_detail WHERE order_num=?"; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, order_num);
				pstmt.executeUpdate();
				
				sql = "DELETE FROM orders WHERE order_num=?"; 
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, order_num);
				pstmt.executeUpdate();
				
				conn.commit();
			} catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		// 관리자, 사용자 주문 상세/
		public OrderVO getOrder(int order_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OrderVO order = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM orders WHERE order_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, order_num);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					order = new OrderVO();
					order.setOrder_num(rs.getInt("order_num"));
					order.setItem_name(rs.getString("item_name"));
					order.setOrder_total(rs.getInt("order_total"));
					order.setOrder_payment(rs.getInt("order_payment"));
					order.setOrder_status(rs.getInt("order_status"));
					order.setOrder_name(rs.getString("order_name"));
					order.setOrder_zipcode(rs.getString("order_zipcode"));
					order.setOrder_address1(rs.getString("order_address1"));
					order.setOrder_address2(rs.getString("order_address2"));
					order.setMem_phone(rs.getString("Mem_phone"));
					order.setOrder_notice(rs.getNString("order_notice"));
					order.setOrder_regdate(rs.getDate("order_regdate"));
					order.setMem_num(rs.getInt("mem_num"));
				}
			} catch(Exception e) {
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return order;
		}
		
		// 관리자, 사용자 주문 수정/
		public void updateOrder(OrderVO order)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			String sub_sql = ""; 
			int cnt = 0;
			
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				OrderVO db_order = getOrder(order.getOrder_num());
				
				if(order.getOrder_status() == 1 && db_order.getOrder_status() == 1) {
					sub_sql += "order_name=?, order_zipcode=?, order_address1=?, order_address2=?, mem_phone=?, order_notice=?,";
				}
				
				sql = "UPDATE orders SET order_status=?," + sub_sql + "order_modifydate = SYSDATE WHERE order_num=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(++cnt, order.getOrder_status());
				if(order.getOrder_status() == 1 && db_order.getOrder_status() == 1) {
					pstmt.setString(++cnt, order.getOrder_name());
					pstmt.setString(++cnt, order.getOrder_zipcode());
					pstmt.setString(++cnt, order.getOrder_address1());
					pstmt.setString(++cnt, order.getOrder_address2());
					pstmt.setString(++cnt, order.getMem_phone());
					pstmt.setString(++cnt, order.getOrder_notice());
				}
				pstmt.setInt(++cnt, order.getOrder_num());
				
				pstmt.executeUpdate();
				
				// 주문 취소일 경우에만 상품 개수 조정
				if(order.getOrder_status() == 5) {
					// 주문 번호에 해당하는 상품 정보 구하기
					List<OrderDetailVO> detailList = getListOrderDetail(order.getOrder_num());
					sql = "UPDATE item SET item_stock = item_stock+? WHERE item_num=?"; 
					pstmt2 = conn.prepareStatement(sql);
					for(int i=0; i<detailList.size(); i++) {
						OrderDetailVO detail = detailList.get(i);
						pstmt2.setInt(1, detail.getOrder_quantity());
						pstmt2.setInt(2, detail.getItem_num());
						pstmt2.addBatch();
						
						// 계속 추가하면 outOfMemory 발생, 기본 문법적으로 100개 단위로 executeBatch()
						if(i%1000 == 0) {
							pstmt2.executeBatch();
						}
					} // end of for
					pstmt2.executeBatch();
				} // end of if
				conn.commit();
				
			} catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			} finally {
				DBUtil.executeClose(null, pstmt2, conn);
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		// 사용자 주문 취소
		public void updateOrderCancel(int order_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				conn.setAutoCommit(false);
				
				sql = "UPDATE orders SET order_status=5, order_modifydate=SYSDATE WHERE order_num=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, order_num);
				pstmt.executeUpdate();
				
				List<OrderDetailVO> detailList = getListOrderDetail(order_num);
				
				// 주문취소로 주문 상품의 재고수를 환원
				sql = "UPDATE item SET item_stock = item_stock + ? WHERE item_num=?";
				
				pstmt2 = conn.prepareStatement(sql);
				for(int i=0; i<detailList.size(); i++) {
					OrderDetailVO detail = detailList.get(i);
					pstmt2.setInt(1, detail.getOrder_quantity());
					pstmt2.setInt(2, detail.getItem_num());
					pstmt2.addBatch();
					
					if(i % 1000 == 0) {
						pstmt2.executeBatch();
					}
				}
				pstmt2.executeBatch();
				conn.commit();
				
			} catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			} DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
			
		}
		
}
























