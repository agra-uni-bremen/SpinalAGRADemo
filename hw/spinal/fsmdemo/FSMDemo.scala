package fsmdemo

import spinal.core._
import spinal.core.sim._

import spinal.lib.fsm._

case class FSMDemo() extends Component {
  val io = new Bundle {
    val result = out Bool()
  }
  val fsm = new StateMachine{
    val stateA = new State with EntryPoint
    val stateB = new State
    val stateC = new State
    val counter = Reg(UInt(8 bits)) init (0)
    
    io.result := False
    
    stateA.whenIsActive(goto(stateB))

    stateB.onEntry(counter := 0)
    stateB.whenIsActive {
      counter := counter + 1
      when(counter === 4){
        goto(stateC)
      }
    }
    stateB.onExit(io.result := True)

    stateC.whenIsActive (goto(stateA))
  }
}

object FSMDemoVerilog extends App {
  Config.spinal.generateVerilog(FSMDemo())
}

object FSMDemoSim extends App {
  Config.sim.compile(FSMDemo()).doSim{ dut =>
    dut.clockDomain.forkStimulus(10)
    dut.clockDomain.waitRisingEdge(10)
    simSuccess()
  }
}
