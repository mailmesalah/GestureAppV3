package com.project.gestureappv2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridItemsAdapter extends BaseAdapter {

	private Context mContext;
	private List<Bitmap> images = new ArrayList<Bitmap>();
	public List<String> imagePaths = new ArrayList<String>();

	public GridItemsAdapter(Context c) {
		mContext = c;

	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return images.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public void setItemAt(int position, Bitmap image) {
		images.add(position, image);
	}

	public void removeItemAt(int position) {
		images.remove(position);
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			// if it's not recycled, initialize some attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
			imageView.setTag(position);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageBitmap(images.get(position));

		return imageView;
	}

	public void loadAllImages() {
		images.clear();

		List<Bitmap> imgByteList = getAllImages();
		for (Iterator iterator = imgByteList.iterator(); iterator.hasNext();) {
			Bitmap bitmap = (Bitmap) iterator.next();
			images.add(bitmap);
		}
		notifyDataSetChanged();
	}

	private List<Bitmap> getAllImages() {
		ArrayList<Bitmap> imageList = new ArrayList<>();

		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath()
				+ ParameterPasser.ImageFolder);
		if (!dir.exists()) {
			dir.mkdir();
		}

		String[] files = dir.list();

		if (files != null) {
			// Sort to read it in order
			sortFilesByNumber(files);
			imagePaths.clear();
			for (String f : files) { // loop and print all file				
				try {
					if (f.lastIndexOf(".txt") == -1) {
						imagePaths.add(f);
						Bitmap bitmap = BitmapFactory.decodeFile(sdCard
								.getAbsolutePath()
								+ ParameterPasser.ImageFolder + "/" + f);
						imageList.add(bitmap);
					}
				} catch (Exception e) {
					Log.d("Error in GridItemsAdapter.getAllImages()",
							e.getMessage());
				}
			}
		}
		return imageList;
	}

	private String[] sortFilesByNumber(String[] files) {

		Arrays.sort(files, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int n1 = extractNumber(o1);
				int n2 = extractNumber(o2);
				return n1 - n2;
			}

			private int extractNumber(String name) {
				int i = 0;
				try {
					int s = name.indexOf('_') + 1;
					int e = name.lastIndexOf('.');
					String number = name.substring(s, e);
					i = Integer.parseInt(number);
				} catch (Exception e) {
					i = 0; // if filename does not match the format
							// then default to 0
				}
				return i;
			}
		});

		return files;
	}
}
