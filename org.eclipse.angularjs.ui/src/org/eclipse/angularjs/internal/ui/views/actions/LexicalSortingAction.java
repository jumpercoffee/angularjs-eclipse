package org.eclipse.angularjs.internal.ui.views.actions;

import org.eclipse.angularjs.core.AngularElement;
import org.eclipse.angularjs.core.BaseModel;
import org.eclipse.angularjs.internal.ui.AngularUIMessages;
import org.eclipse.angularjs.internal.ui.AngularUIPlugin;
import org.eclipse.angularjs.internal.ui.ImageResource;
import org.eclipse.angularjs.internal.ui.views.AngularContentOutlinePage;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.navigator.CommonViewer;

import tern.angular.modules.IAngularElement;

public class LexicalSortingAction extends Action {
	private final AngularContentOutlinePage page;
	private LexicalSorter sorter;

	public LexicalSortingAction(AngularContentOutlinePage page) {
		super(AngularUIMessages.LexicalSortingAction_text, SWT.TOGGLE);
		this.page = page;
		this.sorter = new LexicalSorter();
		super.setToolTipText(AngularUIMessages.LexicalSortingAction_tooltip);
		super.setDescription(AngularUIMessages.LexicalSortingAction_description);
		super.setImageDescriptor(ImageResource.getImageDescriptor(ImageResource.IMG_ELCL_SORT));

		boolean checked = AngularUIPlugin.getDefault().getPreferenceStore()
				.getBoolean("LexicalSortingAction.isChecked");
		valueChanged(checked, false);
	}

	@Override
	public void run() {
		valueChanged(isChecked(), true);
	}

	private void valueChanged(final boolean on, boolean store) {
		setChecked(on);
		final CommonViewer viewer = page.getViewer();
		BusyIndicator.showWhile(viewer.getControl().getDisplay(), new Runnable() {
			@Override
			public void run() {
				if (on) {
					viewer.setSorter(sorter);
				} else {
					viewer.setSorter(null);
				}
			}
		});

		if (store) {
			AngularUIPlugin.getDefault().getPreferenceStore().setValue("LexicalSortingAction.isChecked", on);
		}
	}

	class LexicalSorter extends ViewerSorter {
		@Override
		public int category(Object element) {
			if (element instanceof IAngularElement) {
				IAngularElement angularElement = (IAngularElement) element;
				switch (angularElement.getAngularType()) {
				case controller:
					return 5;
				case directive:
					return 6;
				case filter:
					return 7;
				case factory:
					return 8;
				case provider:
					return 9;
				case service:
					return 10;
				default:
					return 11;
				}
			} else if (element instanceof BaseModel) {
				switch (((BaseModel) element).getType()) {
				case Module:
				case ScriptsFolder:
					return 0;
				default:
					return 1;
				}
			}
			return 12;
		}
	}
}
