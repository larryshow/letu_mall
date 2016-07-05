import java.text.SimpleDateFormat;
import java.util.Date;


public class Te {
	
	public static void main(String[] args) {
		//1459789400662ff
		Date date = new Date(1459787221666L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		System.out.println(str);
	}
}
