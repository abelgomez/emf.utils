package fr.inria.atlanmod.emf.utils.ui.commands;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import fr.inria.atlanmod.emf.utils.AtlanmodUtilsPlugin;
import fr.inria.atlanmod.emf.utils.ui.AtlanmodUiUtilsPlugin;
import fr.inria.atlanmod.emf.utils.ui.ExceptionStatus;

public class RegisterEPackageCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelectionService service = window.getSelectionService();
		IStructuredSelection structuredSelection = (IStructuredSelection) service.getSelection();
	 
		Display display = Display.getCurrent() != null ? Display.getCurrent() : Display.getDefault();
		boolean autoload = MessageDialog.openQuestion(
				display.getActiveShell(), "Load on next restart?",
				"Load the selected resources automatically on the next platform's restart?");
		
		Iterator<?> it = structuredSelection.iterator();
		while (it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof IFile) {
				IFile file = (IFile) obj;
				try {
					URI resourceUri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					EPackage ePackage = AtlanmodUtilsPlugin.getDefault().getRegistryManager().loadEPackage(resourceUri);
					AtlanmodUtilsPlugin.getDefault().getRegistryManager().registerEPackage(ePackage, autoload);
				} catch (Exception e) {
					AtlanmodUiUtilsPlugin.getDefault().getLog().log(ExceptionStatus.createExceptionStatus(e));	
				}
			}
		}
		return null;
	}
}
