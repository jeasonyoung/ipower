package ipower.converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;
import javax.sql.rowset.serial.SerialException;

import org.apache.struts2.util.StrutsTypeConverter;
/**
 * java.sql.Clob转换器。
 * @author young.
 * @since 2013-09-18.
 * */
public class ClobConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if(values == null || values.length == 0) return null;
		if(values[0] != null){
			try {
				return new SerialClob(values[0].toCharArray());
			} catch (SerialException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		if(o instanceof Clob){
			try {
				BufferedReader bufferedRead = new BufferedReader(((Clob)o).getCharacterStream());
				StringBuffer s = new StringBuffer();
				String str = null;
				while((str = bufferedRead.readLine()) != null){
					s.append(str);
				}
				return s.toString();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
}