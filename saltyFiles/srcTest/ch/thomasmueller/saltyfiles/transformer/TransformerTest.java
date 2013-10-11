/*
 www.thomasmueller.ch

 Copyright (C) 2004 Thomas Mueller

 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.

 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */

package ch.thomasmueller.saltyfiles.transformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.crypto.Cipher;

import junit.framework.TestCase;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ch.thomasmueller.saltyfiles.io.InputFileHandler;
import ch.thomasmueller.saltyfiles.io.OutputFileHandler;

/**
 * @author Thomas Mueller
 *  
 */
public class TransformerTest extends TestCase
{

	private Logger log = LogManager.getLogger(this.getClass().getName());

	public void test() throws Exception
	{
        File a = new File("test/a.txt");
        File b = new File("test/b.txt");
        File c = new File("test/c.txt");
		
		Transformer t = new Transformer("a".toCharArray(), new byte[8]);
		
		// cleartext to encripted
		InputFileHandler inHandler = new InputFileHandler(a.getCanonicalPath());
		OutputFileHandler outHandler = new OutputFileHandler(b.getCanonicalPath());
		//TODO fix tests
//		t.transform(Cipher.ENCRYPT_MODE, inHandler, outHandler, );
		log.debug("transformed a to b");
		t= null;
		
		
		t = new Transformer("a".toCharArray(), new byte[8]);
		// encripted to cleartext
		inHandler = new InputFileHandler(b.getCanonicalPath());
		outHandler = new OutputFileHandler(c.getCanonicalPath());
		
	//	t.transform(Cipher.DECRYPT_MODE, inHandler, outHandler);
		log.debug("transformed b to c");
		
        // compare text files

        BufferedReader aReader = new BufferedReader(new FileReader(a));
        BufferedReader bReader = new BufferedReader(new FileReader(b));
        BufferedReader cReader = new BufferedReader(new FileReader(c));
        
        String aLine = null;
        String bLine = null;
        String cLine = null;
        
        while (true)
        {
                aLine = aReader.readLine();
                bLine = bReader.readLine();
                cLine = cReader.readLine();
                if (aLine == null )
                {
                        break;
                }
                
                assertEquals(aLine, cLine);
                assertTrue(! aLine.equals(bLine));
                log.debug("A: " +  aLine);
                log.debug("B: " +  bLine);
                log.debug("C: " +  cLine);                

        }
	}
	

	
		
	
	

}