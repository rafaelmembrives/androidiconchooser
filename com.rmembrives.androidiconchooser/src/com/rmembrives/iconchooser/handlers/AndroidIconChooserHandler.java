package com.rmembrives.iconchooser.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

public class AndroidIconChooserHandler extends AbstractHandler {

	public AndroidIconChooserHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String path;
		IProject project;

		// Get the project path
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
			Object firstElement = selection.getFirstElement();
			if (firstElement instanceof IAdaptable) {
				project = (IProject) ((IAdaptable) firstElement).getAdapter(IProject.class);
				path = project.getLocation().toString();
				System.out.println(path);
			} else
				return null;
		} else {
			return null;
		}

		Shell shell = window.getShell();

		// Show the dialog
		AndroidIconChooserDialog dialog = new AndroidIconChooserDialog(window.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {

			// Copy the icons
			for (String icon : dialog.getSelectedIcons()) {
				for (String density : new String[] { "hdpi", "mdpi", "xhdpi" }) {
					try {
						URL iconsPathURL = new URL("platform:/plugin/com.rmembrives.androidiconchooser/icons/" + dialog.getTheme() + "/" + density + "/" + icon);
						InputStream inputStream = iconsPathURL.openConnection().getInputStream();
						IFile file = project.getFile("res/drawable-" + density + File.separator + icon);
						file.create(inputStream, true, null);
					} catch (Exception e) {
						new MessageDialog(shell, "Android Icon Chooser", null, "Failed to copy icon: " + dialog.getTheme() + "/drawable-" + density + "/" + icon + " " + e.getMessage(), MessageDialog.ERROR, new String[] { "OK" }, 0).open();
						e.printStackTrace();
					}
				}
			}

		}
		return null;
	}

	class AndroidIconChooserDialog extends Dialog {

		String[] selectedIcons = new String[] {};

		TableViewer viewer;
		String theme;
		Button add;

		String[] iconsNames = new String[] { "action_about.png", "action_help.png", "action_search.png", "action_settings.png", "alerts_and_states_airplane_mode_off.png", "alerts_and_states_airplane_mode_on.png", "alerts_and_states_error.png", "alerts_and_states_warning.png", "av_add_to_queue.png", "av_download.png", "av_fast_forward.png", "av_full_screen.png", "av_make_available_offline.png", "av_next.png", "av_pause_over_video.png", "av_pause.png", "av_play_over_video.png", "av_play.png", "av_previous.png", "av_repeat.png", "av_replay.png", "av_return_from_full_screen.png", "av_rewind.png", "av_shuffle.png", "av_stop.png", "av_upload.png", "collections_cloud.png", "collections_collection.png", "collections_go_to_today.png", "collections_labels.png", "collections_new_label.png", "collections_sort_by_size.png", "collections_view_as_grid.png", "collections_view_as_list.png", "content_attachment.png", "content_backspace.png", "content_copy.png", "content_cut.png", "content_discard.png", "content_edit.png", "content_email.png", "content_event.png", "content_import_export.png", "content_merge.png", "content_new_attachment.png", "content_new_email.png", "content_new_event.png", "content_new_picture.png", "content_new.png", "content_paste.png", "content_picture.png", "content_read.png", "content_remove.png", "content_save.png", "content_select_all.png", "content_split.png", "content_undo.png", "content_unread.png", "device_access_accounts.png", "device_access_add_alarm.png", "device_access_alarms.png", "device_access_battery.png", "device_access_bightness_low.png", "device_access_bluetooth_connected.png", "device_access_bluetooth_searching.png", "device_access_bluetooth.png", "device_access_brightness_auto.png", "device_access_brightness_high.png", "device_access_brightness_medium.png", "device_access_call.png", "device_access_camera.png", "device_access_data_usage.png", "device_access_dial_pad.png", "device_access_end_call.png", "device_access_flash_automatic.png", "device_access_flash_off.png", "device_access_flash_on.png", "device_access_location_found.png", "device_access_location_off.png", "device_access_location_searching.png", "device_access_mic_muted.png", "device_access_mic.png", "device_access_network_cell.png", "device_access_network_wifi.png", "device_access_new_account.png", "device_access_not_secure.png", "device_access_ring_volume.png", "device_access_screen_locked_to_landscape.png", "device_access_screen_locked_to_portrait.png", "device_access_screen_rotation.png", "device_access_sd_storage.png", "device_access_secure.png", "device_access_storage.png", "device_access_switch_camera.png", "device_access_switch_video.png", "device_access_time.png", "device_access_usb.png", "device_access_video.png", "device_access_volume_muted.png", "device_access_volume_on.png", "hardware_computer.png", "hardware_dock.png", "hardware_gamepad.png", "hardware_headphones.png", "hardware_headset.png", "hardware_keyboard.png", "hardware_mouse.png", "hardware_phone.png", "images_crop.png", "images_rotate_left.png", "images_rotate_right.png", "images_slideshow.png", "location_directions.png", "location_map.png", "location_place.png", "location_web_site.png", "navigation_accept.png", "navigation_back.png", "navigation_cancel.png", "navigation_collapse.png", "navigation_expand.png", "navigation_forward.png", "navigation_next_item.png", "navigation_previous_item.png", "navigation_refresh.png", "rating_bad.png", "rating_favorite.png", "rating_good.png", "rating_half_important.png", "rating_important.png", "rating_not_important.png", "social_add_group.png", "social_add_person.png", "social_cc_bcc.png", "social_chat.png", "social_forward.png", "social_group.png", "social_person.png", "social_reply_all.png", "social_reply.png", "social_send_now.png", "social_share.png" };

		HashMap<String, Image> iconsImages = new HashMap<String, Image>();

		protected AndroidIconChooserDialog(Shell parentShell) {
			super(parentShell);

			try {
				LocalResourceManager lrm = new LocalResourceManager(JFaceResources.getResources(), parentShell);

				for (int i = 0; i < iconsNames.length; i++) {
					iconsImages.put(iconsNames[i], lrm.createImage(ImageDescriptor.createFromFile(this.getClass(), "/icons/holo_light/mdpi/" + iconsNames[i])));
				}
			} catch (Exception IGNORE) {
			}

		}

		class ViewContentProvider implements IStructuredContentProvider {
			public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object parent) {
				return iconsNames;
			}
		}

		class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
			public String getColumnText(Object obj, int index) {
				return (String) obj;
			}

			public Image getColumnImage(Object obj, int index) {
				return iconsImages.get(obj);
			}
		}

		@Override
		protected Control createDialogArea(Composite parent) {

			GridData gridData;

			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			parent.setLayout(layout);

			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			Combo combo = new Combo(parent, SWT.READ_ONLY);
			combo.setLayoutData(gridData);
			combo.add("Holo Dark");
			combo.add("Holo Light");
			combo.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent event) {
					theme = ((Combo) event.widget).getSelectionIndex() == 0 ? "holo_dark" : "holo_light";
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent event) {
				}
			});
			combo.select(0);
			theme = "holo_dark";

			gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;

			viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			viewer.setContentProvider(new ViewContentProvider());
			viewer.setLabelProvider(new ViewLabelProvider());
			viewer.setInput(iconsImages);
			viewer.getControl().setLayoutData(gridData);
			viewer.addSelectionChangedListener(new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					if (!selection.isEmpty()) {
						assert (add != null);
						add.setEnabled(true);
						selectedIcons = (String[]) selection.toList().toArray(new String[selection.size()]);
					} else {
						assert (add != null);
						selectedIcons = new String[] {};
						add.setEnabled(false);
					}

				}
			});

			return parent;
		}

		@Override
		protected void createButtonsForButtonBar(Composite parent) {
			GridData gridData = new GridData();
			gridData.verticalAlignment = GridData.FILL;
			gridData.horizontalSpan = 0;
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = false;
			gridData.horizontalAlignment = SWT.RIGHT;

			parent.setLayoutData(gridData);

			add = createOkButton(parent, OK, "Add", true);
			add.setEnabled(false);

			Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
			cancelButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					setReturnCode(CANCEL);
					close();
				}
			});
		}

		protected Button createOkButton(Composite parent, int id, String label, boolean defaultButton) {
			((GridLayout) parent.getLayout()).numColumns++;
			Button button = new Button(parent, SWT.PUSH);
			button.setText(label);
			button.setFont(JFaceResources.getDialogFont());
			button.setData(new Integer(id));
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					okPressed();
				}
			});
			if (defaultButton) {
				Shell shell = parent.getShell();
				if (shell != null) {
					shell.setDefaultButton(button);
				}
			}
			setButtonLayoutData(button);
			return button;
		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		@Override
		protected void okPressed() {
			setReturnCode(OK);
			super.okPressed();
		}

		public String getTheme() {
			return theme;
		}

		public String[] getSelectedIcons() {
			return selectedIcons;
		}

	}
}
