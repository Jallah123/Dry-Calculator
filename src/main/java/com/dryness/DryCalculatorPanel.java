/*
 * Copyright (c) 2018 Abex
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.dryness;

import static com.dryness.DryCalculatorService.calculateDryness;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

public class DryCalculatorPanel extends PluginPanel
{
	private final JTextField droprate = new JTextField();
	private final JTextField drops = new JTextField();
	private final JTextField kc = new JTextField();
	private final JTextArea result = new JTextArea();
	private final ImageIcon resultIcon = new ImageIcon();

	DryCalculatorPanel()
	{
		result.setLineWrap(true);
		result.setEditable(false);
		JButton calculate = new JButton();
		calculate.setText("Calculate");
		calculate.addActionListener(this::handleClick);

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));


		JPanel dryPanel = new JPanel();
		dryPanel.setLayout(new GridLayout(0, 1));
		JLabel title = new JLabel();
		title.setFont(FontManager.getDefaultBoldFont());
		title.setText("Dryness calculator");

		JLabel dropRateLabel = new JLabel();
		dropRateLabel.setText("Droprate");

		JLabel kcLabel = new JLabel();
		kcLabel.setText("Kc");

		JLabel dropsLabel = new JLabel();
		dropsLabel.setText("Amount of drops");

		dryPanel.add(title);

		dryPanel.add(dropRateLabel);
		dryPanel.add(droprate);

		dryPanel.add(kcLabel);
		dryPanel.add(kc);

		dryPanel.add(dropsLabel);
		dryPanel.add(drops);

		dryPanel.add(calculate);

		add(dryPanel, BorderLayout.NORTH);
		add(result, BorderLayout.CENTER);
		JLabel resultImage = new JLabel(resultIcon);
		add(resultImage, BorderLayout.SOUTH);
	}

	private void handleClick(final ActionEvent actionEvent)
	{
		DrynessInput input = null;
		try
		{
			input = new DrynessInput(droprate.getText(), kc.getText(), drops.getText());
		}
		catch (Exception exception)
		{
			result.setText("Invalid input: " + exception.getMessage());
			resultIcon.setImage(null);
		}
		if (input != null)
		{
			DrynessResult drynessResult = calculateDryness(input);
			result.setText(drynessResult.getText());
			resultIcon.setImage(drynessResult.getImage());
		}
	}
}
