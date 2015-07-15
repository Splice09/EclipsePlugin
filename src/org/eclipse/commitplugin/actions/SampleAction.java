package org.eclipse.commitplugin.actions;

import java.io.File;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate{
	
	
	private String commitType;
	private String commitMessageString;
	private String finalCommitMessage;
	private Text commitMessage;
	
	private Display display;
	private final Shell shell;
	
	
	/**
	 * The constructor.
	 */
	public SampleAction() {
		display = Display.getDefault();
		shell = new Shell(display);
	}
	
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */

	public void run(IAction action) {
		initialize();
	}
	
	public void initialize(){
		//create shell object
		if(shell.isVisible() == false){
			//create layout object and set shell layout
			FormLayout layout = new FormLayout();
			shell.setLayout(layout);
		        
			//draw UI
			drawUI();
		 
			//set shell title, and size
			shell.setText("Git Commit Plugin");
			shell.setSize(450, 200);
			centerWindow(shell);
			//open shell
			shell.open();
			
		}
	}

	/*
	 * This void function draws the UI of the shell
	 */
	public void drawUI(){
		System.out.println("We are drawing the ui.");
		//create new buttons with green, red, and refactor labels
        Button greenBtn = new Button(shell, SWT.PUSH);
        greenBtn.setText("Green Light");
        Button redBtn = new Button(shell, SWT.PUSH);
        redBtn.setText("Red Light");
        Button refBtn = new Button(shell, SWT.PUSH);
        refBtn.setText("Refactor");
        
        //positon the three buttons
        FormData refData = new FormData(80, 30);
        refData.right = new FormAttachment(98);
        refData.bottom = new FormAttachment(95);
        refBtn.setLayoutData(refData);
        
        FormData redData = new FormData(80, 30);
        redData.right = new FormAttachment(98);
        redData.bottom = new FormAttachment(refBtn, -35, SWT.BOTTOM);
        redBtn.setLayoutData(redData);
        
        FormData greenData = new FormData(80, 30);
        greenData.right = new FormAttachment(98);
        greenData.bottom = new FormAttachment(refBtn, -70, SWT.BOTTOM);
        greenBtn.setLayoutData(greenData);
        
      //create text box
        commitMessage = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormData textData = new FormData();
        textData.width = 300;
        textData.height = 140;
        textData.top = new FormAttachment(5);
        textData.left = new FormAttachment(2);
        commitMessage.setToolTipText("Add additional commit comments here");
        commitMessage.setLayoutData(textData);
        
        //create listener for button presses
        greenBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	makeString(commitMessage, 1);
            	shell.setVisible(false);
            }
        });
        redBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	makeString(commitMessage, 2);
            	shell.setVisible(false);
            }
        });
        refBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	makeString(commitMessage, 3);
            	shell.setVisible(false);
            }
        });
	}
	
	/*
	 * This method concatenates the text with the button selection
	 */
	public void makeString(Text commit,int option){
		System.out.println("we are making the string.");
		commitMessageString = commit.getText().toString();
		switch(option){
			case 1: commitType = "Green Light | ";
					break;
			case 2: commitType = "Red Light | ";
					break;
			case 3: commitType = "Refactor | ";
					break;
		}
		finalCommitMessage = commitType + commitMessageString;
		makeCommit(finalCommitMessage);
	}
	
	/*
	 * This method handles the git commands.
	 */
	public void makeCommit(String commitMessage){
		System.out.println("We are making the commit.");
		//get the current working directory of the user
		String workingDir = System.getProperty("user.dir");
		System.out.println("The user dir is:" + workingDir);
		try {
			//Create the repository
			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			Repository localRepo = builder.setGitDir(new File(workingDir + "/.git"))
					.readEnvironment() // scan environment GIT_* variables
					.findGitDir() // scan up the file system tree
					.build();
	        Git git = new Git(localRepo);
	        //Add all relevant files to the Git repository
	        git.add().addFilepattern(".").call();
	        //Make Commit with included message
	        git.commit().setMessage(commitMessage).call();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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


	
	/*
	 * This method centers the plugin window on the screen upon open.
	*/
	private void centerWindow(Shell shell){
		
		Rectangle bds = shell.getDisplay().getBounds();

        Point p = shell.getSize();

        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;

        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		
	}
}
