package com.project.gestureappv2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ImageActivity extends ActionBarActivity {

	private final static int SELECT_IMAGE = 1;
	private static int currentSelection = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new DashboardFragment()).commit();
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class DashboardFragment extends Fragment {

		public DashboardFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_dashboard,
					container, false);

			GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
			gridview.setAdapter(new GridItemsAdapter(rootView.getContext()));

			((GridItemsAdapter) gridview.getAdapter()).loadAllImages();

			gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					SimpleDateFormat datenTime = new SimpleDateFormat(
							"dd/MM/yyyy h:mm:ss a ");
					String dateS = datenTime.format(new Date());
					String text = dateS + " :: Long Press Position X= "
							+ view.getX() + " - Y= " + view.getY() + " \n";
					RecordInFile.writeToText(text, "Drag.txt");
					if (position > AdapterView.INVALID_POSITION) {

						currentSelection = position;

						int count = parent.getChildCount();
						for (int i = 0; i < count; i++) {
							View curr = parent.getChildAt(i);

							curr.setOnDragListener(new View.OnDragListener() {

								@Override
								public boolean onDrag(View v, DragEvent event) {

									SimpleDateFormat sdf = new SimpleDateFormat(
											"dd/MM/yyyy h:mm:ss a ");
									String time = "";
									String text = "";

									boolean result = true;
									int action = event.getAction();
									switch (action) {
									case DragEvent.ACTION_DRAG_STARTED:
										break;
									case DragEvent.ACTION_DRAG_LOCATION:
										break;
									case DragEvent.ACTION_DRAG_ENTERED:
										time = sdf.format(new Date());
										text = time + " :: Drag Entered X= "
												+ v.getX() + " - Y= "
												+ v.getY() + " \n";
										RecordInFile.writeToText(text,
												"Drag.txt");
										// v.setBackgroundResource(R.drawable.shape_image_view_small_gallery_selected);
										break;
									case DragEvent.ACTION_DRAG_EXITED:
										// v.setBackgroundResource(R.drawable.shape_image_view_small_gallery_unselected);
										break;
									case DragEvent.ACTION_DROP:
										time = sdf.format(new Date());
										text = time + " :: Drag Dropped X= "
												+ v.getX() + " - Y= "
												+ v.getY() + " \n";
										RecordInFile.writeToText(text,
												"Drag.txt");

										if (event.getLocalState() == v) {
											result = false;
										} else {
											View droped = (View) event
													.getLocalState();
											int dropItemPos = ((Integer) droped
													.getTag());

											GridView parent = (GridView) droped
													.getParent();
											GridItemsAdapter adapter = (GridItemsAdapter) parent
													.getAdapter();

											View target = v;
											int targetItemPos = (Integer) target
													.getTag();
											// Update Image names

											File dropFile = new File(
													Environment
															.getExternalStorageDirectory()
															.getAbsolutePath()
															+ ParameterPasser.ImageFolder
															+ "/"
															+ adapter.imagePaths
																	.get(dropItemPos));
											File targetFile = new File(
													Environment
															.getExternalStorageDirectory()
															.getAbsolutePath()
															+ ParameterPasser.ImageFolder
															+ "/"
															+ adapter.imagePaths
																	.get(targetItemPos));

											File tempFile = new File(
													Environment
															.getExternalStorageDirectory()
															.getAbsolutePath()
															+ ParameterPasser.ImageFolder
															+ "/Temp");
											dropFile.renameTo(tempFile);
											targetFile.renameTo(dropFile);
											tempFile.renameTo(targetFile);

											adapter.loadAllImages();
											adapter.notifyDataSetChanged();

										}
										break;
									case DragEvent.ACTION_DRAG_ENDED:
										// v.setBackgroundResource(R.drawable.shape_image_view_small_gallery_unselected);
										break;
									default:
										result = false;
										break;
									}
									return result;
								}
							});
						}

						int relativePosition = position
								- parent.getFirstVisiblePosition();

						View target = (View) parent
								.getChildAt(relativePosition);

						ClipData data = ClipData.newPlainText("DragData",
								"Checking");
						target.startDrag(data, new View.DragShadowBuilder(
								target), target, 0);
					}

					return false;
				}
			});

			// Image Show
			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					try {

						// Toast.makeText(getActivity(), "position " +
						// position,Toast.LENGTH_SHORT).show();

						GridItemsAdapter adapter = (GridItemsAdapter) parent
								.getAdapter();
						ParameterPasser.image = (Bitmap) adapter
								.getItem(position);
						ParameterPasser.currentImageIndex=position;

						if (ParameterPasser.selectedActivity == ParameterPasser.ZOOM_ACTIVITY) {
							Intent i = new Intent(getActivity(),
									ZoomActivity.class);						
							startActivity(i);

						} else if (ParameterPasser.selectedActivity == ParameterPasser.FLIP_ACTIVITY) {
							Intent i = new Intent(getActivity(),
									FlipActivity.class);						
							startActivity(i);

						} else if (ParameterPasser.selectedActivity == ParameterPasser.FLING_ACTIVITY) {
							Intent i = new Intent(getActivity(),
									FlingActivity.class);						
							startActivity(i);

						} else if (ParameterPasser.selectedActivity == ParameterPasser.OTHER_ACTIVITY) {
							Intent i = new Intent(getActivity(),
									OtherActivity.class);						
							startActivity(i);
						}
						

					} catch (Exception e) {

					}

				}

			});

			Button bAddImage = (Button) rootView.findViewById(R.id.add_image);
			bAddImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// in onCreate or any event where your want the user to
					// select a file
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(
							Intent.createChooser(intent, "Select Picture"),
							SELECT_IMAGE);

				}

			});

			Button bRemoveImage = (Button) rootView
					.findViewById(R.id.remove_image);
			bRemoveImage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (currentSelection > -1) {

						// get the grid adapter
						GridView gv = (GridView) (getActivity()
								.findViewById(R.id.gridview));
						GridItemsAdapter gia = (GridItemsAdapter) gv
								.getAdapter();

						// Delete the image
						// ...............................................
						File fImage = new File(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath()
								+ ParameterPasser.ImageFolder
								+ "/"
								+ gia.imagePaths.get(currentSelection));
						boolean deleted = fImage.delete();

						// Update all image positions of images in the folder
						if (deleted) {

							for (int i = currentSelection + 1; i < gia
									.getCount(); ++i) {
								File from = new File(Environment
										.getExternalStorageDirectory()
										.getAbsolutePath()
										+ ParameterPasser.ImageFolder
										+ "/"
										+ gia.imagePaths.get(i));
								File to = new File(Environment
										.getExternalStorageDirectory()
										.getAbsolutePath()
										+ ParameterPasser.ImageFolder
										+ "/"
										+ gia.imagePaths.get(i - 1));
								from.renameTo(to);
							}

							gia.loadAllImages();
							gia.notifyDataSetChanged();
							currentSelection = -1;
						}
					}
				}

			});

			return rootView;
		}

		// UPDATED
		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// Toast.makeText(getActivity(), "Returned",
			// Toast.LENGTH_SHORT).show();
			if (resultCode == RESULT_OK) {
				if (requestCode == SELECT_IMAGE) {
					Uri selectedImageUri = data.getData();
					String selectedImagePath;
					String filemanagerstring;
					// OI FILE Manager
					filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					selectedImagePath = getPath(selectedImageUri);

					// NOW WE HAVE OUR WANTED STRING
					if (selectedImagePath != null) {
						// Toast.makeText(getActivity(), selectedImagePath,
						// Toast.LENGTH_SHORT).show();
						// System.out.println("selectedImagePath is the right one for you!");
						saveImage(selectedImagePath);
					} else {
						// Toast.makeText(getActivity(), filemanagerstring,
						// Toast.LENGTH_SHORT).show();
						// System.out.println("filemanagerstring is the right one for you!");
						saveImage(filemanagerstring);
					}

					// Update it on Screen
					GridView gridview = (GridView) getActivity().findViewById(
							R.id.gridview);
					GridItemsAdapter gAdapter = (GridItemsAdapter) gridview
							.getAdapter();
					gAdapter.loadAllImages();
				}
			}
		}

		private void saveImage(String imagePath) {
			File imgFile = new File(imagePath);

			if (imgFile.exists()) {

				Bitmap bitmap = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());
				// Copy the file to a local folder with order no
				try {
					OutputStream out;
					File sdCard = Environment.getExternalStorageDirectory();
					File dir = new File(sdCard.getAbsolutePath()
							+ ParameterPasser.ImageFolder);
					if (!dir.exists()) {
						dir.mkdir();
					}

					GridView gridview = (GridView) getActivity().findViewById(
							R.id.gridview);
					GridItemsAdapter gAdapter = (GridItemsAdapter) gridview
							.getAdapter();

					String ext = imgFile.getName();
					ext = ext.substring(ext.lastIndexOf("."));
					File file = new File(dir + File.separator + "Image_"
							+ gAdapter.getCount() + ext);
					file.createNewFile();
					out = new FileOutputStream(file);

					out.write(getBitmapAsByteArray(bitmap));
					out.close();
				} catch (Exception e) {
					Log.d("Error in ImageActivity.saveImage()", e.getMessage());
				}
			}
		}

		public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 0, outputStream);
			return outputStream.toByteArray();
		}

		// UPDATED!
		public String getPath(Uri uri) {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(uri,
					projection, null, null, null);
			if (cursor != null) {
				// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
				// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE
				// MEDIA
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				return cursor.getString(column_index);
			} else
				return null;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Propagating to Fragment
		// Toast.makeText(this, "Reached",
		// Toast.LENGTH_SHORT).show();
		super.onActivityResult(requestCode, resultCode, data);
	}
}
