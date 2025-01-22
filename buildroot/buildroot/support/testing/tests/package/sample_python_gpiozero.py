#  StarshipOS Copyright (c) 2025. R.A. James

from gpiozero import pi_info

piBoardInfo = pi_info('a020d3')  # 3B+

assert(piBoardInfo.model == '3B+')
