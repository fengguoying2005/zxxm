import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JTextArea;


public class Find {
	static JTextArea text = new JTextArea();
	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame();
        frame.setTitle("backup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocation(200, 30);
        frame.setVisible(true);
		frame.getContentPane().add(text);
		long begintime = System.currentTimeMillis();
		String line = "C:\\Users\\admin\\Desktop\\rvpkgdemo\\,checkpoint,pas";
		if(line!=null) {
			String[] strs = line.split(",");
			if(strs!=null && strs.length==3 && !"".equals(strs[0])) {
				File f = new File(strs[0].trim());
				find(f,strs[1].trim(),strs[2].trim());
			}
		}
		long lasttime = System.currentTimeMillis();
		text.setText(text.getText()+"\n\n"+"spire:"+(lasttime-begintime)/60000+" minutes"+((lasttime-begintime)/1000)%60+"seconds");
	}
	public static void find(File f, String info, String fix) throws IOException {
		if(f.isDirectory()) {
			File[] files = f.listFiles();
			for(File _f:files) {
				find(_f, info, fix);
			}
		}
		if(f.isFile()) {
			if(f.getName().endsWith(fix)) {
				System.out.println(f.getName());
				BufferedReader r = new BufferedReader(new FileReader(f));
				String line = r.readLine();
				while(line!=null) {
					//System.out.println(line);
					if(line.toLowerCase().indexOf(info)!=-1 && !texts.contains(f.getAbsolutePath())) {
						text.setText(text.getText()+"\n"+("info<"+info+">in dictionary:"+f.getAbsolutePath()));
						texts.add(f.getAbsolutePath());
					}
					line = r.readLine();
				}
			}
		}
	}
	static Set<String> texts = new HashSet<String>();
}
