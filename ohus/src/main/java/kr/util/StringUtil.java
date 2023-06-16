package kr.util;

public class StringUtil {
	
	/*------------
	 HTML 태그를 허용하면서 줄바꿈 하기
	 -------------*/
	public static String useBrHtml(String str) { //문자열을 받아 static 메서드로 처리
		
		if(str==null) return null; //문자가 없으면 null
		
		//문자가 있으면 \r\n이 있는 경우 <br>로 대체해서 줄바꿈 해주기
		return str.replaceAll("\r\n", "<br>") 
						.replaceAll("\r","<br>")
						.replaceAll("\n", "<br>");
	}
	
	
	/*-------------
	 HTML 태그를 허용하지 않으면서 줄바꿈 하기
	 -------------*/
	
	public static String useBrNoHtml(String str) {
		
		if(str == null) return null;
		
		return str.replaceAll("<", "&lt;") //html 허용하지 않기 위해 <> 무력화 시키기 -- 문자 < > 로 해주는 거
					   .replaceAll(">", "&gt;")
					   .replaceAll("\r\n", "<br>")
					   .replaceAll("\r", "<br>")
					   .replaceAll("\n", "<br>");
	}
	
	/*-------------
	 HTML를 허용하지 않음
	 -------------*/
	
	public static String useNoHtml(String str) {
		if(str==null) return null;
		
		return str.replaceAll("<", "&lt;")
					    .replaceAll(">", "&gt;");
	}
	
	/*-------------
		큰 따옴표 처리
	 -------------*/
	public static String parseQuot(String str) {
		if(str==null) return null;
		return str.replaceAll("\"", "&quot;");
	}
	
	/*-------------
	문자열을 지정한 문자열 개수 이후 ... 처리 (긴 문자열 ... 처리)
 	-------------*/
	public static String shortWords(int length, String content) { //length : 지정한 길이
		if(content==null) return null;
		
		if(content.length() > length) {
			return content.substring(0, length) + "...";
		}
		
		return content; //지정한 길이가 더 길면 줄일 필요 없이 그냥 출력
	}
}
