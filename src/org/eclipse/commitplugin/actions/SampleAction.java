package org.eclipse.commitplugin.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.net.URLDecoder;

import javax.swing.JOptionPane;

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


/**
 * Author: Patrick Fleming
 * Date:   7/15/2015
 * Email:  jpf0005@auburn.edu
 */

public class SampleAction implements IWorkbenchWindowActionDelegate{
	
	
	private String commitType;
	private String commitMessageString;
	private String finalCommitMessage;
	private String workingDir;
	private String gitPath;
	private Text commitMessage;
	
	private FileReader fr;
	private BufferedReader textReader;
	private Display display;
	private final Shell shell;
	
	
	/*
	 * Constructor.
	 */
	public SampleAction() {
		display = Display.getDefault();
		shell = new Shell(display, SWT.APPLICATION_MODAL);
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
	 * This method handles the Git commands.
	 */
	public void makeCommit(String commitMessage){
		//get the current working directory of the user (set in arguments.txt)
		workingDir = getWorkingDir();
		if((workingDir == "") || (workingDir == null)){
			JOptionPane.showMessageDialog(null, "Unable to make commit. Please set a path in arguments.txt");
		}
		else if(workingDir == "1"){
			System.out.println("message handled.");
		}
		else{
			try {
				//Create the repository
				File gitDir = new File(workingDir);
				Git git = Git.init().setDirectory(gitDir).call();
				//Add all relevant files to the Git repository
				git.add().addFilepattern(".").call();
				//Make Commit with included message
				git.commit().setMessage(commitMessage).call();
				git.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/* 
	 * This function opens the arguments.txt file and retrieves the specified git root directory for commits. 
	 */
	public String getWorkingDir(){
		String myDir = SampleAction.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			//Build file path to arguments.txt
			String myFileName = new File(myDir).getName();
			String newPath = myDir.substring(0, myDir.length() - myFileName.length());
			String finalPath = newPath + "arguments.txt";
			//Open arguments to retrieve Git Commit path
			try {
				//JOptionPane.showMessageDialog(null, "final path: " + finalPath);
				fr = new FileReader(finalPath);
				textReader = new BufferedReader(fr);
				//Read first line of arguments
				try {
					gitPath = textReader.readLine();
				} catch (IOException e) {
					gitPath = "";
					e.printStackTrace();
				}
			}catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "arguments.txt not found.");
				gitPath = "1";
				e.printStackTrace();
			}	
		return gitPath;
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
		// dummy comment
		
	}
}
