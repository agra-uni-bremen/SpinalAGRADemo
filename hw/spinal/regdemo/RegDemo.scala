package regdemo

import spinal.core._

// Hardware definition
case class RegDemo() extends Component {
  val io = new Bundle {
    val cond = in  Bool()
    val myReg = out UInt(4 bits)
    val myRegInit = out UInt(4 bits)
  }

  val mySignal = Bool()
  val myRegister = Reg(UInt(4 bits))
  val myRegisterWithReset = Reg(UInt(4 bits)) init(0)
  mySignal := False
  when(io.cond) {
      mySignal := True
      myRegister := myRegister + 1
      myRegisterWithReset := myRegisterWithReset + 1
  }
  io.myReg := myRegister
  io.myRegInit := myRegisterWithReset
}

object RegDemoVerilog extends App {
  Config.spinal.generateVerilog(RegDemo())
}

object RegDemoVhdl extends App {
  Config.spinal.generateVhdl(RegDemo())
}
