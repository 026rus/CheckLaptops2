import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JFormattedTextField;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetingsUI extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField frmtdtxtfldServerip;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			SetingsUI dialog = new SetingsUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SetingsUI()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(Setings.getMainIcon()));
		setBounds(100, 100, 444, 265);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblServerIp = new JLabel("Server IP: ");
			GridBagConstraints gbc_lblServerIp = new GridBagConstraints();
			gbc_lblServerIp.insets = new Insets(0, 0, 0, 5);
			gbc_lblServerIp.anchor = GridBagConstraints.EAST;
			gbc_lblServerIp.gridx = 0;
			gbc_lblServerIp.gridy = 0;
			contentPanel.add(lblServerIp, gbc_lblServerIp);
		}
		{
			frmtdtxtfldServerip = new JFormattedTextField(Setings.getServerIP());
			GridBagConstraints gbc_frmtdtxtfldServerip = new GridBagConstraints();
			gbc_frmtdtxtfldServerip.fill = GridBagConstraints.HORIZONTAL;
			gbc_frmtdtxtfldServerip.gridx = 1;
			gbc_frmtdtxtfldServerip.gridy = 0;
			contentPanel.add(frmtdtxtfldServerip, gbc_frmtdtxtfldServerip);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						Setings.setServerIP(frmtdtxtfldServerip.getText());
						Setings.save();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
