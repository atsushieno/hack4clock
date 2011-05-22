package hack4jp.hack4clock;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import net.arnx.jsonic.JSON;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageTask extends AsyncTask<Object, Object, ImageView> {

	//ImageView target;

	String url;

	public ImageTask(/*ImageView iv, */String apiUrl) {

		//target = iv;
		url = apiUrl;
	}

	@Override
	protected ImageView doInBackground(Object... params) {

		List<HashMap<String, Object>> list = getDataFromURL(url);
		saveImages(list);

		return null;
	}

	@Override
	protected void onPostExecute(ImageView view) {
		
	}

	private void setImg(ImageView iv, String url) {

		URL mUrl;
		try {
			mUrl = new URL(url);
			mUrl.openStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void saveImages(List<HashMap<String, Object>> list) {

		String fileDir = "/sdcard/fclock/";
		String urlDir = url.substring(0, url.lastIndexOf("/") + 1);
		int index = 0;
		try {
			for (HashMap<String, Object> item : list) {

				String fileName = (String) item.get("file");

				File fDir = new File(fileDir);
				if (!fDir.exists()) {
					fDir.mkdirs();
				}
				File fFile = new File(fileDir, fileName);
				if (!fFile.exists()) {
					fFile.createNewFile();
				}

				String fileUrl = urlDir + fileName;
				byte[] data = getByteArrayFromURL(fileUrl);

				FileOutputStream fout = new FileOutputStream(fFile);
				fout.write(data);
				fout.close();

				index++;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * URLから
	 * 
	 * @param strUrl
	 * @return
	 */
	private List<HashMap<String, Object>> getDataFromURL(String strUrl) {
		InputStream is = null;
		List<HashMap<String, Object>> list = null;
		try {
			is = (new URL(url)).openStream();
			list = JSON.decode(is, List.class);
			FileWriter fw = new java.io.FileWriter ("/sdcard/fclock/files.json", true);
			fw.append(JSON.encode(list));
			fw.close();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return (List<HashMap<String, Object>>) list.get(0);
	}

	private byte[] getByteArrayFromURL(String strUrl) {
		byte[] byteArray = new byte[1024];
		byte[] result = null;
		HttpURLConnection con = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		int size = 0;
		try {
			URL url = new URL(strUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			in = con.getInputStream();

			out = new ByteArrayOutputStream();
			while ((size = in.read(byteArray)) != -1) {
				out.write(byteArray, 0, size);
			}
			result = out.toByteArray();
		} catch (Exception e) {
			Log.e("mstssk", e.getLocalizedMessage(), e);
		} finally {
			try {
				if (con != null)
					con.disconnect();
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (Exception e) {
				Log.e("mstssk", e.getLocalizedMessage(), e);
			}
		}
		return result;
	}

}