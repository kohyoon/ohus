package kr.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import kr.cart.vo.CartVO;
import kr.item.vo.ItemVO;
import kr.util.DBUtil;

public class CartDAO {
	// 싱글턴 패턴
	private static CartDAO instance = new CartDAO();
	public static CartDAO getInstance() {
		return instance;
	}
	private CartDAO() {}
	
	// 장바구니 등록
	public void insertCart(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO cart (cart_num, item_num, order_quantity, mem_num) "
					+ "VALUES (cart_seq.nextval,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getItem_num());
			pstmt.setInt(2, cart.getOrder_quantity());
			pstmt.setInt(3, cart.getMem_num());
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	// 회원번호별 총 구매액
	public int getTotalByMem_num(int mem_num)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int total = 0;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			// 장바구니에 담긴 주문의 가격을 구하여 sub_total에 저장
			sql = "SELECT SUM(sub_total) FROM "
					+ "(SELECT c.mem_num, c.order_quantity * i.item_price as sub_total "
					+ "FROM cart c JOIN item i ON c.item_num = i.item_num) WHERE mem_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return total;
	}
	// 장바구니 목록
	public List<CartVO> getListCart(int mem_num)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CartVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM cart c "
					+ "JOIN item i ON c.item_num = i.item_num "
					+ "WHERE c.mem_num = ? ORDER BY i.item_num ASC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<CartVO>();
			while(rs.next()) {
				CartVO cart = new CartVO();
				cart.setCart_num(rs.getInt("cart_num"));
				cart.setItem_num(rs.getInt("item_num"));
				cart.setOrder_quantity(rs.getInt("order_quantity"));
				cart.setMem_num(rs.getInt("mem_num"));
				
				// 상품 정보를 담기 위한 ItemVO 객체 생성
				ItemVO item = new ItemVO();
				item.setItem_name(rs.getString("item_name"));
				item.setItem_price(rs.getInt("item_price"));
				item.setItem_photo1(rs.getString("item_photo1"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_status(rs.getInt("item_status"));
				
				// ItemVO를 CartVO에 저장
				cart.setItemVO(item);
				
				// 같은 상품의 총구매 금액 구하기
				cart.setSub_total(cart.getOrder_quantity() * item.getItem_price());
				list.add(cart);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	// 장바구니 상세
	public CartVO getCart(CartVO cart)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CartVO cartSaved = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM cart WHERE item_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			// ?에 데이터 바인딩
			pstmt.setInt(1, cart.getItem_num());
			pstmt.setInt(2, cart.getMem_num());
			// SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cartSaved = new CartVO();
				cartSaved.setCart_num(rs.getInt("cart_num"));
				cartSaved.setItem_num(rs.getInt("item_num"));
				cartSaved.setOrder_quantity(rs.getInt("order_quantity"));
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return cartSaved;
	}
	
	// 장바구니 수정 (개별 상품 수량 설정)
	public void updateCart(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE cart SET order_quantity=? WHERE cart_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getOrder_quantity());
			pstmt.setInt(2, cart.getCart_num());
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	// 장바구니 상품번호와 회원번호별 수정
	public void updateCartByItem_num(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE cart SET order_quantity=? WHERE item_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cart.getOrder_quantity());
			pstmt.setInt(2, cart.getItem_num());
			pstmt.setInt(3, cart.getMem_num());
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	// 장바구니 삭제
	public void deleteCart(int cart_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM cart WHERE cart_num=?";
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart_num);
			//SQL문 실행
			pstmt.executeUpdate();			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	 
}





















