package adders

import spinal.core._
import spinal.core.sim._

object AddersSim extends App {
  Config.sim.compile(FullAdder()).doSim { dut =>
    // Fork a process to generate the reset and the clock on the dut
    dut.clockDomain.forkStimulus(period = 10)

  }
}
