package com.abs192.Nittefiles.misc;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class logtheshit {

	Context context;
	String ROOT;

	public logtheshit(Context context) {
		this.context = context;
		ROOT = context.getFilesDir().getPath();
		checkTheFiles();
	}

	private void checkTheFiles() {
		try {
			File myDirectory = new File(ROOT);
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}
			File user = new File(ROOT + "/user.txt");
			if (!user.exists()) {
				user.createNewFile();
				clearLoginData();
			}
			File pass = new File(ROOT + "/pass.txt");
			if (!pass.exists()) {
				pass.createNewFile();
				clearLoginData();
			}
			File check = new File(ROOT + "/check.txt");
			if (!check.exists()) {
				check.createNewFile();
				clearLoginData();
			}
			File defaultAdd = new File(ROOT + "/defaultAdd.txt");
			if (!defaultAdd.exists()) {
				defaultAdd.createNewFile();
				overwrite("defaultAdd", "txt", "http://192.168.201.1:81/");
			}

			File branch = new File(ROOT + "/branch.txt");
			if (!branch.exists()) {
				branch.createNewFile();
				overwrite("branch", "txt", "None");
			}
			File page = new File(ROOT + "/defaultPage.txt");
			if (!page.exists()) {
				page.createNewFile();
				overwrite("defaultPage", "txt", "0");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getDefaultAdd() {
		return read("defaultAdd", "txt");

	}

	public void setDefaultAdd(String url) {
		overwrite("defaultAdd", "txt", url);
	}

	public void overwrite(String name, String extension, String body) {

		// File file = new File(ROOT + "/log.txt");

		try {
			FileOutputStream fos = context.openFileOutput(name.trim() + "."
					+ extension.trim(), Context.MODE_PRIVATE);
			fos.write(body.getBytes());
			fos.close();
		} catch (FileNotFoundException f) {
			Toast.makeText(context, "File Not Found Exception",
					Toast.LENGTH_SHORT).show();
			f.printStackTrace();

		} catch (IOException e) {
			Toast.makeText(context, "IO Exception", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public String read(String name, String extension) {

		int ch;
		StringBuffer fileContent = new StringBuffer("");
		FileInputStream fis;
		try {
			fis = context.openFileInput(name.trim() + "." + extension.trim());
			try {
				while ((ch = fis.read()) != -1)
					fileContent.append((char) ch);
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}

		String data = new String(fileContent);
		return data;
	}

	public void clearLoginData() {
		try {
			overwrite("user", "txt", "null");
			overwrite("pass", "txt", "null");
			overwrite("check", "txt", "f");
		} catch (RuntimeException e) {

		} catch (Exception e) {

		}
	}

	public void setLoginData(String user, String pass) {
		overwrite("user", "txt", user);
		overwrite("pass", "txt", pass);
	}

	public String getUser() {
		return read("user", "txt");
	}

	public String getPass() {
		return read("pass", "txt");
	}

	public boolean getcheckBox() {
		String A = read("check", "txt");
		if (A.trim().equals("t")) {
			return true;
		}
		return false;
	}

	public void setcheckBox(boolean a) {
		String A = (a) ? "t" : "f";
		overwrite("check", "txt", A);
	}

	public boolean isLoginDataEmpty() {
		String u = getUser();
		String p = getPass();
		if (u.equals("null") && p.equals("null"))
			return true;
		return false;
	}

	public void setBranch(String a) {
		if (a != null) {
			overwrite("branch", "txt", a);
			if (a.equals("CSE")) {
				overwrite("defaultAdd", "txt",
						"http://192.168.201.1:81/");
			} else if (a.equals("None")) {
				overwrite("defaultAdd", "txt", "http://192.168.201.1:81/");
			}
		}
	}

	public String getBranch() {
		return read("branch", "txt");
	}

	public void setDefaultPage(String a) {
		if (a != null) {
			if (a.equals("Files")) {
				overwrite("defaultPage", "txt", "0");
			} else if (a.equals("Websis")) {
				overwrite("defaultPage", "txt", "1");
			} else if (a.equals("Offline MITFILES"))
				overwrite("defaultPage", "txt", "2");
		}
	}

	public int getDefaultPage() {
		try {
			return Integer.parseInt(read("defaultPage", "txt"));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}