package com.dryness;

import java.math.BigDecimal;
import java.math.MathContext;

public class DrynessInput
{
	private final BigDecimal chance;
	private final int kc;
	private final int amountOfDrops;


	public DrynessInput(final String dropRateString, final String kcString, final String amountOfDropsString)
	{
		if (dropRateString.split("/").length != 2)
		{
			throw new RuntimeException("Invalid drop rate format, correct example: 1/100");
		}
		BigDecimal first = BigDecimal.valueOf(Long.parseLong(dropRateString.split("/")[0]));
		BigDecimal second = BigDecimal.valueOf(Long.parseLong(dropRateString.split("/")[1]));
		try
		{
			this.chance = first.divide(second, MathContext.DECIMAL128);
		}
		catch (ArithmeticException e)
		{
			throw new RuntimeException("Invalid drop rate numbers");
		}
		try
		{
			this.kc = Integer.parseInt(kcString);
		}
		catch (NumberFormatException e)
		{
			throw new RuntimeException("Invalid kc number: " + kcString);
		}
		try
		{
			this.amountOfDrops = Integer.parseInt(amountOfDropsString);
		}
		catch (NumberFormatException e)
		{
			throw new RuntimeException("Invalid drops number: " + kcString);
		}

		if (amountOfDrops > kc)
		{
			throw new RuntimeException("More drops than kc?!");
		}
		if (kc == 0)
		{
			throw new RuntimeException("Go get some kills!");
		}
		if (kc > 100000)
		{
			throw new RuntimeException("KC should be lower than 100.000");
		}
		if (chance.intValue() >= 1)
		{
			throw new RuntimeException("Chance bigger than 1?!");
		}
	}

	public BigDecimal getChance()
	{
		return chance;
	}

	public int getKc()
	{
		return kc;
	}

	public int getAmountOfDrops()
	{
		return amountOfDrops;
	}
}
