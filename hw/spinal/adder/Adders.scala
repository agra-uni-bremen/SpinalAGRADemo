package adders

import spinal.core._


case class HalfAdder() extends Component {
	val io = new Bundle {
		val a = in(Bits(1 bits))
		val b = in(Bits(1 bits))
		val sum = out(Bits(1 bits))
		val cout = out(Bits(1 bits))
	}
	io.sum := io.a ^ io.b
	io.cout := io.a & io.b
}

case class FullAdder(structural : Boolean = false) extends Component {
	val io = new Bundle {
		val a = in(Bits(1 bits))
		val b = in(Bits(1 bits))
		val cin = in(Bits(1 bits))
		val sum = out(Bits(1 bits))
		val cout = out(Bits(1 bits))
	}
	
	val ha1 = structural generate (new HalfAdder())
	val ha2 = structural generate (new HalfAdder())
	if(structural) {
		ha1.io.a := io.a
		ha1.io.b := io.b
		ha2.io.a := io.cin
		ha2.io.b := ha1.io.sum
		io.sum := ha2.io.sum
		io.cout := ha1.io.cout | ha2.io.cout
	} else {		
		io.sum := io.a ^ io.b ^ io.cin
		io.cout := io.a & io.b | io.a & io.cin | io.b & io.cin
	}
}

object AddersVerilog extends App {
  Config.spinal.generateVerilog(HalfAdder())
  Config.spinal.generateVerilog(FullAdder())
}
