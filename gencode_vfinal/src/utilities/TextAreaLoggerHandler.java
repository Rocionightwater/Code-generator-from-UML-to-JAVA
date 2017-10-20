package utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaLoggerHandler extends Handler {

    private JTextArea textArea;
	
    public TextAreaLoggerHandler(JTextArea textArea) {
    	this.textArea = textArea;
    }
    
	@Override
	public void publish(final LogRecord record) {
       SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	SimpleDateFormat dt = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
            	Date now = new Date();
  
            	
                StringWriter text = new StringWriter();
                PrintWriter out = new PrintWriter(text);
                out.println(textArea.getText());
                out.printf("[%s] %s", (dt.format(now)).toString(), record.getMessage());
                textArea.setText(text.toString());
            }
        });
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}    

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub

	}

}
