package adders

import spinal.core._

case class AdderConfig(
	adderBits : Int = 8,
	structuralFA : Boolean = false
) {
	assert(
		assertion = adderBits >= 1,
		message = "Bit width of adder is too low (>= 1 required)"
	)
}

case class RippleCarryAdder(adderConfig : AdderConfig) extends Component {
	val io = new Bundle {
		val a = in(Bits(adderConfig.adderBits bits))
		val b = in(Bits(adderConfig.adderBits bits))
		val sum = out(Bits(adderConfig.adderBits bits))
		val cout = out(Bits(1 bits))
	}
	
	val adderArray : Array[FullAdder] = Array.fill(adderConfig.adderBits)(FullAdder(adderConfig.structuralFA))
	for(i <- 0 to adderConfig.adderBits-1) {
		adderArray(i).io.a := io.a(i).asBits
		adderArray(i).io.b := io.b(i).asBits
		io.sum(i) := adderArray(i).io.sum.asBool
		if(i == 0){
			adderArray(i).io.cin := 0
		} else {
			adderArray(i).io.cin := adderArray(i-1).io.cout
		}
	}
	io.cout := adderArray.last.io.cout
}

object RCAVerilog extends App {
	Config.spinal.generateVerilog(new RippleCarryAdder(AdderConfig(adderBits = 16, structuralFA = true)))
}
