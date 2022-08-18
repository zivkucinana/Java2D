package drawing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.border.TitledBorder;

import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import stack.DlgStack;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Cursor;

public class FrmDrawing extends JFrame {

	private final int OPERATION_DRAWING = 1;
	private final int OPERATION_EDIT_DELETE = 0;
	
	private int activeOperation = OPERATION_DRAWING;
	
	private PnlDrawing pnlDrawing = new PnlDrawing();
	private ButtonGroup btnsOperation = new ButtonGroup();
	private ButtonGroup btnsShapes = new ButtonGroup();
	
	private JToggleButton btnOperationDrawing = new JToggleButton("Crtanje");
	private JToggleButton btnOperationEditOrDelete = new JToggleButton("Selektuj");
	private JButton btnActionEdit = new JButton("Izmeni");
	private JButton btnActionDelete = new JButton("Obrisi");
	private JToggleButton btnShapePoint = new JToggleButton("Tacka");
	private JToggleButton btnShapeLine = new JToggleButton("Linija");
	private JToggleButton btnShapeRectangle = new JToggleButton("Pravougaonik");
	private JToggleButton btnShapeCircle = new JToggleButton("Krug");
	private JToggleButton btnShapeDonut = new JToggleButton("Krofna");
	private JButton btnColorEdge = new JButton("Boja linije");
	private JButton btnColorInner = new JButton("Boja unutrasnjosti");
	
	private Color edgeColor = Color.BLACK, innerColor = Color.WHITE;
	
	private boolean lineWaitingForSecondPoint = false;
	private Point lineFirstPoint;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setTitle("IT31-Ana Zivkucin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pnlDrawing.addMouseListener(pnlDrawingClickListener());
		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		btnOperationDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationDrawing();
			}
		});
		btnOperationDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationDrawing);
		panel_2.add(btnOperationDrawing);
		
		btnOperationEditOrDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationEditDelete();
			}
		});
		btnOperationEditOrDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationEditOrDelete);
		panel_2.add(btnOperationEditOrDelete);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		btnActionEdit.addActionListener(btnActionEditClickListener());
		btnActionEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnActionEdit);
		
		btnActionDelete.addActionListener(btnActionDeleteClickListener());
		btnActionDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnActionDelete);
		
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		btnShapePoint.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapePoint);
		panel_4.add(btnShapePoint);
		
		btnShapeLine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeLine);
		panel_4.add(btnShapeLine);
		
		btnShapeRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeRectangle);
		panel_4.add(btnShapeRectangle);
		
		btnShapeCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeCircle);
		panel_4.add(btnShapeCircle);
		
		btnShapeDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeDonut);
		panel_4.add(btnShapeDonut);
		
		btnOperationDrawing.setSelected(true);
		setOperationDrawing();
		btnShapePoint.setSelected(true);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
	
		btnColorEdge = new JButton("Boja ivice");
		btnColorEdge.addActionListener(btnColorEdgeClickListener());
		btnColorEdge.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_5.add(btnColorEdge);
		
		btnColorInner = new JButton("Boja unutrasnjosti");
		btnColorInner.addActionListener(btnColorInnerClickListener());
		btnColorInner.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_5.add(btnColorInner);
	}
	
	private MouseAdapter pnlDrawingClickListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point mouseClick = new Point(e.getX(), e.getY());
				pnlDrawing.deselect();
				if (!btnShapeLine.isSelected()) lineWaitingForSecondPoint = false;
				
				if (activeOperation == OPERATION_EDIT_DELETE) {
					pnlDrawing.select(mouseClick);
					return;
				}
				
				if (btnShapePoint.isSelected()) {
					pnlDrawing.addShape(new Point(mouseClick.getX(), mouseClick.getY(), edgeColor));
					return;
				} else if (btnShapeLine.isSelected()) {
					if (lineWaitingForSecondPoint) {
						pnlDrawing.addShape(new Line(lineFirstPoint, mouseClick, edgeColor));
						lineWaitingForSecondPoint = false;
						return;
					}
					
					lineFirstPoint = mouseClick;
					lineWaitingForSecondPoint = true;
					return;
				} else if (btnShapeRectangle.isSelected()) {
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.setPoint(mouseClick);
					dlgRectangle.setColors(edgeColor, innerColor);
					dlgRectangle.setVisible(true);
					
					if(dlgRectangle.getRectangle() != null) pnlDrawing.addShape(dlgRectangle.getRectangle());
					return;
				} else if (btnShapeCircle.isSelected()) {
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.setPoint(mouseClick);
					dlgCircle.setColors(edgeColor, innerColor);
					dlgCircle.setVisible(true);
					
					if(dlgCircle.getCircle() != null) pnlDrawing.addShape(dlgCircle.getCircle());
					return;
				} else if (btnShapeDonut.isSelected()) {
					DlgDonut dlgDonut = new DlgDonut();
					dlgDonut.setPoint(mouseClick);
					dlgDonut.setColors(edgeColor, innerColor);
					dlgDonut.setVisible(true);
					
					if(dlgDonut.getDonut() != null) pnlDrawing.addShape(dlgDonut.getDonut());
					return;
				}
			}
		};
	}
	
	private ActionListener btnActionEditClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = pnlDrawing.getSelected();
				if (index == -1) return;
				
				Shape shape = pnlDrawing.getShape(index);
				
				if (shape instanceof Point) {
					DlgPoint dlgPoint = new DlgPoint();
					dlgPoint.setPoint((Point)shape);
					dlgPoint.setVisible(true);
					
					if(dlgPoint.getPoint() != null) {
						pnlDrawing.setShape(index, dlgPoint.getPoint());
						pnlDrawing.repaint();
					}
				} else if (shape instanceof Line) {
					DlgLine dlgLine = new DlgLine();
					dlgLine.setLine((Line)shape);
					dlgLine.setVisible(true);
					
					if(dlgLine.getLine() != null) {
						pnlDrawing.setShape(index, dlgLine.getLine());
						pnlDrawing.repaint();
					}
				} else if (shape instanceof Rectangle) {
					DlgRectangle dlgRectangle = new DlgRectangle();
					dlgRectangle.setRectangle((Rectangle)shape);
					dlgRectangle.setVisible(true);
					
					if(dlgRectangle.getRectangle() != null) {
						pnlDrawing.setShape(index, dlgRectangle.getRectangle());
						pnlDrawing.repaint();
					}
				
				}else if (shape instanceof Donut) {
						DlgDonut dlgDonut = new DlgDonut();
						dlgDonut.setDonut((Donut)shape);
						dlgDonut.setVisible(true);
						
						if(dlgDonut.getDonut() != null) {
							pnlDrawing.setShape(index, dlgDonut.getDonut());
							pnlDrawing.repaint();
						}
				} else if (shape instanceof Circle) {
					DlgCircle dlgCircle = new DlgCircle();
					dlgCircle.setCircle((Circle)shape);
					dlgCircle.setVisible(true);
					
					if(dlgCircle.getCircle() != null) {
						pnlDrawing.setShape(index, dlgCircle.getCircle());
						pnlDrawing.repaint();
					}
				} 
			}
		};
	}
	
	private ActionListener btnActionDeleteClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pnlDrawing.isEmpty()) return;
				if (JOptionPane.showConfirmDialog(null, "Da li zaista zelite da obrisete selektovane oblike?", "Potvrda", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) pnlDrawing.removeSelected();
			}
		};
	}
	

	private ActionListener btnColorEdgeClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Izaberite boju ivice", edgeColor);
				if (edgeColor == null) edgeColor = Color.BLACK;
			}
		};
	}
	
	private ActionListener btnColorInnerClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerColor = JColorChooser.showDialog(null, "Izaberite boju unutrasnjosti", innerColor);
				if (innerColor == null) innerColor = Color.WHITE;
			}
		};
	}

	private void setOperationDrawing() {
		activeOperation = OPERATION_DRAWING;
		
		pnlDrawing.deselect();
		
		btnActionEdit.setEnabled(false);
		btnActionDelete.setEnabled(false);
		
		btnShapePoint.setEnabled(true);
		btnShapeLine.setEnabled(true);
		btnShapeRectangle.setEnabled(true);
		btnShapeCircle.setEnabled(true);
		btnShapeDonut.setEnabled(true);
		
		btnColorEdge.setEnabled(true);
		btnColorInner.setEnabled(true);
	}
	
	private void setOperationEditDelete() {
		activeOperation = OPERATION_EDIT_DELETE;
		
		btnActionEdit.setEnabled(true);
		btnActionDelete.setEnabled(true);
		
		btnShapePoint.setEnabled(false);
		btnShapeLine.setEnabled(false);
		btnShapeRectangle.setEnabled(false);
		btnShapeCircle.setEnabled(false);
		btnShapeDonut.setEnabled(false);
		
		btnColorEdge.setEnabled(false);
		btnColorInner.setEnabled(false);
	}
}
