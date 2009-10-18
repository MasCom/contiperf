/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU Lesser General Public License (LGPL), Eclipse Public License (EPL) 
 * and the BSD License.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.contiperf.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.databene.contiperf.ExecutionLogger;
import org.databene.contiperf.Util;

public class FileExecutionLogger implements ExecutionLogger {
	
	private static final String FILENAME = "target/contiperf/contiperf.log";
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static boolean firstCall = true;

	public FileExecutionLogger() {
		if (firstCall) {
			File file = new File(FILENAME);
			ensureDirectoryExists(file.getParentFile());	
			if (file.exists())
				file.delete();
			firstCall = false;
		}
    }

	private void ensureDirectoryExists(File dir) {
	    File parent = dir.getParentFile();
	    if (!dir.exists()) {
	    	ensureDirectoryExists(parent);
	    	dir.mkdir();
	    }
    }

	public void logSummary(String id, long elapsedTime, long invocationCount, long startTime) {
		OutputStream out = null;
        String message = id + "," + (elapsedTime / 1000000) + ',' 
        	+ invocationCount + ',' + (startTime / 1000000) + LINE_SEPARATOR;
		try {
	        out = new FileOutputStream(FILENAME, true);
	        out.write(message.getBytes());
        } catch (IOException e) {
	        e.printStackTrace();
        } finally {
	        Util.close(out);
        }
    }

}