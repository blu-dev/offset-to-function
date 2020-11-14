//Given a properly formatted .txt file, it will assign function labels to any offset provided
//@author blu-dev
//@category Functions
//@keybinding 
//@menupath 
//@toolbar 

import ghidra.app.script.GhidraScript;
import ghidra.program.model.util.*;
import ghidra.program.model.reloc.*;
import ghidra.program.model.data.*;
import ghidra.program.model.block.*;
import ghidra.program.model.symbol.*;
import ghidra.program.model.scalar.*;
import ghidra.program.model.mem.*;
import ghidra.program.model.listing.*;
import ghidra.program.model.lang.*;
import ghidra.program.model.pcode.*;
import ghidra.program.model.address.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.*;

public class OffsetToFunction extends GhidraScript {

    public void run() throws Exception {
//TODO Add User Code Here
	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt");
	chooser.setFileFilter(filter);
	int returnVal = chooser.showOpenDialog(null);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
		File file = chooser.getSelectedFile();
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		for (int i = 0; i < lines.size(); i++) {
			int colon = lines.get(i).indexOf(':');
			String name = lines.get(i).substring(0, colon);
			Address addr = parseAddress(lines.get(i).substring(colon + 1));
			Function func = getFunctionAt(addr);
			if (func == null) {
                func = createFunction(addr, name);
                if (func == null)
                    throw new RuntimeException("Unable to create function " + name + " at " + Long.toHexString(addr.getOffset()));
            }
            else {
                func.setName(name, SourceType.DEFAULT);
            }
		}
	}
    }

}
