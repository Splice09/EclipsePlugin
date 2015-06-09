package org.eclipse.commitplugin.actions;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate{
	private IWorkbenchWindow window;
	
	private String commitType;
	private String commitMessage;
	private boolean defaultButton = false;
	/**
	 * The constructor.
	 */
	public SampleAction() {
		
	}
	

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */

	public void run(IAction action) {
		//create shell object
		Shell shell = new Shell(window.getShell());
		//create layout object
		RowLayout layout = new RowLayout();
		//set layout margins
        layout.marginLeft = 50;
        layout.marginTop = 50;
        shell.setLayout(layout);
        //create new button with cancel label
        Button quitBtn = new Button(shell, SWT.PUSH);
        quitBtn.setText("Cancel");
        quitBtn.setLayoutData(new RowData(80, 30));
        //create listener for button press
        quitBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.setVisible(false);
            }
        });
        //set shell title, and size
        shell.setText("Git Commit Plugin");
        shell.setSize(250, 200);
        //open shell
        shell.open();

	}


	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}