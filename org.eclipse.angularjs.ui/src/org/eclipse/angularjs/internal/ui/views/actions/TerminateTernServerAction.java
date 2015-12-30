/**
 *  Copyright (c) 2013-2014 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.angularjs.internal.ui.views.actions;

import org.eclipse.angularjs.internal.ui.AngularUIMessages;
import org.eclipse.angularjs.internal.ui.Trace;
import org.eclipse.angularjs.internal.ui.views.AngularContentOutlinePage;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;

import tern.eclipse.ide.core.IIDETernProject;
import tern.eclipse.ide.core.TernCorePlugin;
import tern.eclipse.ide.ui.ImageResource;

/**
 * This action stops the tern server of the current tern project.
 * 
 */
public class TerminateTernServerAction extends Action {

	private final AngularContentOutlinePage page;

	public TerminateTernServerAction(AngularContentOutlinePage page) {
		this.page = page;
		super.setText(AngularUIMessages.TerminateTernServerAction_text);
		super.setToolTipText(AngularUIMessages.TerminateTernServerAction_tooltip);
		super.setImageDescriptor(ImageResource.getImageDescriptor(ImageResource.IMG_STOP_ENABLED));
	}

	@Override
	public void run() {
		try {
			IProject project = page.getProject();
			IIDETernProject ternProject = TernCorePlugin.getTernProject(project);
			if (ternProject != null) {
				ternProject.disposeServer();
			}
		} catch (CoreException e) {
			Trace.trace(Trace.SEVERE, "Error while getting tern project.", e);
		}
	}
}
