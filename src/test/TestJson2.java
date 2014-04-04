package test;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class TestJson2 {

	public static void main(String[] args) {
		List<Menu1> root = new ArrayList<Menu1>();
		for(int i = 0; i < 2; i ++) {
			Menu1 data = new Menu1();//一级菜单
			data.setText(""+i);
			for(int j = 0; j < 2; j ++) {
				Menu2 data2 = new Menu2();//二级菜单
				data2.setText(i + "," + j);
				data2.setUrl(i + "," + j);
				data.getChildren().add(data2);
			}
			
			root.add(data );
		}
		//System.out.println(net.sf.json.JSONObject.fromObject(root).toString());
		JSONArray json2 = JSONArray.fromObject(root );
		System.out.println(json2.toString());
	}
}

