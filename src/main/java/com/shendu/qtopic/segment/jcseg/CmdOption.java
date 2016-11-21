package com.shendu.qtopic.segment.jcseg;

import org.kohsuke.args4j.*;

public class CmdOption {
	
	@Option(name="-in", usage="Specify input directory")
	public String in = "examples/dataset/";
	
	@Option(name="-out", usage="Specify output directory")
	public String out = "examples/seg-result/jcseg/";
	
}
