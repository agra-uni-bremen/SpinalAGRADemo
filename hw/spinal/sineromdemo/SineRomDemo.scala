package sineromdemo

import spinal.core._
import spinal.core.sim._

case class SineRomDemo(resolutionWidth: Int, sampleCount: Int) extends Component {
  val io = new Bundle {
    val sin = out SInt(resolutionWidth bits)
    val sinFiltered = out SInt(resolutionWidth bits)
  }
  // Calculate values for the lookup table
  def sinTable = for(sampleIndex <- 0 until sampleCount) yield {
    val sinValue = Math.sin(2 * Math.PI * sampleIndex / sampleCount)
    S((sinValue * ((1<<resolutionWidth)/2-1)).toInt,resolutionWidth bits)
  }
  val rom =  Mem(SInt(resolutionWidth bits),initialContent = sinTable)
  val phase = Reg(UInt(log2Up(sampleCount) bits)) init 0
  phase := phase + 1
    io.sin := rom.readSync(phase)
    io.sinFiltered := RegNext(io.sinFiltered  - (io.sinFiltered  >> 5) + (io.sin >> 5)) init 0
}

object SineRomDemoVerilog extends App {
  Config.spinal.generateVerilog(SineRomDemo(10, 2048))
}

object SineRomDemoSim extends App {
  Config.sim.compile(SineRomDemo(10, 512)).doSim{ dut =>
    dut.clockDomain.forkStimulus(10)
    dut.clockDomain.waitRisingEdge(4096)
    simSuccess()
  }
}
