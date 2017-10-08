package com.alvo.utils

import com.alvo.MainstreamHater
import com.alvo.constants.Constants

/**
  * Created by alvo on 20.05.17.
  */
object MetricsTranslator {
  def convertMeterToPixel(meter: Float): Float = meter * Constants.PX_M_SCALE
  def convertPixelToMeter(pix: Float): Float = pix / Constants.PX_M_SCALE

  def widthInMeter: Float = convertPixelToMeter(MainstreamHater.width)
  def heightInMeter: Float = convertPixelToMeter(MainstreamHater.height)

  def halfWidthInMeter: Float = widthInMeter * 0.5f
  def halfHeightInMeter: Float = heightInMeter * 0.5f
}
