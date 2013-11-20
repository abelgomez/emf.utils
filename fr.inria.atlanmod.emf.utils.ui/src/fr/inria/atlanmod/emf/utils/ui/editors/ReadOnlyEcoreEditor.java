package fr.inria.atlanmod.emf.utils.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.presentation.EcoreEditorPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;

public class ReadOnlyEcoreEditor extends EcoreEditor {
	

	
	public static final String ID = "fr.inria.atlanmod.emf.utils.ui.readonlyEcoreEditor";

	public void createModel() {
		if(getEditorInput() instanceof EPackageEditorInput) {
			EPackage pkg = ((EPackageEditorInput)getEditorInput()).getPackage();
			editingDomain.getResourceSet().getResources().add(pkg.eResource());

			return;
		}
			
		super.createModel();
	}

	@Override
	public void doSaveAs() {
		SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
		saveAsDialog.create();
		saveAsDialog.setMessage(EcoreEditorPlugin.INSTANCE.getString("_UI_SaveAs_message"));
		saveAsDialog.open();
		IPath path = saveAsDialog.getResult();
		if (path != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file != null) {
				ResourceSet resourceSet = editingDomain.getResourceSet();
				Resource currentResource = resourceSet.getResources().get(0);
				URI newURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
				
				Resource newResource = resourceSet.createResource(newURI);
				newResource.getContents().addAll(EcoreUtil.copyAll(currentResource.getContents()));
				resourceSet.getResources().remove(0);
				resourceSet.getResources().move(0, newResource);

				IFileEditorInput modelFile = new FileEditorInput(file);
				setInputWithNotify(modelFile);
				setPartName(file.getName());
				doSave(getActionBars().getStatusLineManager().getProgressMonitor());
			}
		}
	}
}
