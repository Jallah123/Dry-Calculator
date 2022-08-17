package com.dryness;

import java.awt.Image;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.runelite.client.util.ImageUtil;

public class DryCalculatorService
{

	private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
	private static final Image SKELETON;
	private static final Image DOG;
	private static final Image FUCK;
	private static final Image CRY;
	private static final Image NOTHING;
	private static final Image OPRAH;

	static
	{
		SKELETON = ImageUtil.loadImageResource(DryCalculatorPanel.class, "skeleton.png");
		DOG = ImageUtil.loadImageResource(DryCalculatorPanel.class, "dog.png");
		FUCK = ImageUtil.loadImageResource(DryCalculatorPanel.class, "fuck.png");
		CRY = ImageUtil.loadImageResource(DryCalculatorPanel.class, "cry.png");
		NOTHING = ImageUtil.loadImageResource(DryCalculatorPanel.class, "nothing.png");
		OPRAH = ImageUtil.loadImageResource(DryCalculatorPanel.class, "oprah.png");
	}

	public static DrynessResult calculateDryness(final DrynessInput input)
	{
		if (input.getAmountOfDrops() == 0)
		{
			final BigDecimal dryness = toPercentage(BigDecimal.ONE.subtract(input.getChance()).pow(input.getKc()));
			return new DrynessResult(String.format("With %s kc you had a\n%s%% chance of no drops\n%s%% chance of at least 1\nU rn", input.getKc(), dryness, ONE_HUNDRED.subtract(dryness)), getDrynessImage(dryness.doubleValue(), input.getAmountOfDrops()));
		}
		else
		{
			final int choose = choose(input.getKc(), input.getAmountOfDrops());
			BigDecimal dryness = BigDecimal.valueOf(choose).multiply(input.getChance().pow(input.getAmountOfDrops()).multiply(BigDecimal.ONE.subtract(input.getChance()).pow(input.getKc() - input.getAmountOfDrops())));
			BigDecimal dryness2 = BigDecimal.ZERO;
			for (int i = 0; i <= input.getAmountOfDrops(); i++)
			{
				dryness2 = dryness2.add(BigDecimal.valueOf(choose(input.getKc(), i)).multiply(input.getChance().pow(i)).multiply(BigDecimal.ONE.subtract(input.getChance()).pow(input.getKc() - i)));
			}
			BigDecimal dryness3 = toPercentage(BigDecimal.ONE.subtract(dryness2).add(dryness));
			dryness2 = toPercentage(dryness2);
			dryness = toPercentage(dryness);
			final Image drynessImage;
			if (input.getAmountOfDrops() >= 1)
			{
				drynessImage = getDrynessImage(dryness2.doubleValue(), input.getAmountOfDrops());
			}
			else
			{
				drynessImage = getDrynessImage(dryness.doubleValue(), input.getAmountOfDrops());
			}
			return new DrynessResult(String.format("With %s kc you had a\n%s%% chance for exactly %s\n%s%% chance for %s or fewer\n%s%% chance for more\nU rn", input.getKc(), dryness, input.getAmountOfDrops(), dryness2, input.getAmountOfDrops(), dryness3), drynessImage);
		}
	}

	private static Image getDrynessImage(double dryness, int drops)
	{
		if (drops == 0)
		{
			if (dryness > 50)
			{
				return NOTHING;
			}
		}
		if (dryness <= 5)
		{
			return SKELETON;
		}
		if (dryness <= 10)
		{
			return DOG;
		}
		if (dryness <= 25)
		{
			return FUCK;
		}
		if (dryness <= 50)
		{
			return CRY;
		}
		if (dryness <= 75)
		{
			return NOTHING;
		}
		else
		{
			return OPRAH;
		}
	}

	private static BigDecimal toPercentage(final BigDecimal input)
	{
		return input.multiply(ONE_HUNDRED).setScale(4, RoundingMode.HALF_UP);
	}

	private static int choose(int n, int k)
	{
		if (k < 0 || k > n)
		{
			return 0;
		}
		if (k == 0 || k == n)
		{
			return 1;
		}
		k = Math.min(k, n - k);
		int c = 1;
		for (int i = 0; i <= k - 1; i++)
		{
			c = c * (n - i) / (i + 1);
		}
		return c;
	}
}
