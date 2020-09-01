package hospital.clients;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import hospital.dto.ElevatorInfoDTO;

public class Front extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private ClientController controller;

    public Front() throws RemoteException, MalformedURLException, NotBoundException {
        initComponents();
        this.controller = new ClientController();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldFloorElevator1 = new javax.swing.JTextField();
        jTextFieldFloorElevator2 = new javax.swing.JTextField();
        jTextFieldFloorElevator3 = new javax.swing.JTextField();
        jTextFieldPeopleCounterElevator2 = new javax.swing.JTextField();
        jTextFieldDestinationsElevator1 = new javax.swing.JTextField();
        jTextFieldPeopleCounterElevator3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextFieldPeopleCounterElevator1 = new javax.swing.JTextField();
        jTextFieldDestinationsElevator2 = new javax.swing.JTextField();
        jTextFieldDestinationsElevator3 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextFieldPeopleFloor2 = new javax.swing.JTextField();
        jTextFieldPeopleFloor1 = new javax.swing.JTextField();
        jTextFieldPeopleFloor3 = new javax.swing.JTextField();
        jTextFieldPeopleFloor4 = new javax.swing.JTextField();
        jTextFieldPeopleFloor5 = new javax.swing.JTextField();
        jTextFieldPeopleFloor6 = new javax.swing.JTextField();
        jTextFieldPeopleFloor7 = new javax.swing.JTextField();
        jTextFieldPeopleFloor8 = new javax.swing.JTextField();
        jTextFieldPeopleFloor9 = new javax.swing.JTextField();
        jTextFieldPeopleFloor10 = new javax.swing.JTextField();
        jTextFieldPeopleFloor11 = new javax.swing.JTextField();
        jTextFieldPeopleFloor12 = new javax.swing.JTextField();
        jTextFieldPeopleFloor13 = new javax.swing.JTextField();
        jTextFieldPeopleFloor14 = new javax.swing.JTextField();
        jTextFieldPeopleFloor15 = new javax.swing.JTextField();
        jTextFieldPeopleFloor16 = new javax.swing.JTextField();
        jTextFieldPeopleFloor17 = new javax.swing.JTextField();
        jTextFieldPeopleFloor18 = new javax.swing.JTextField();
        jTextFieldPeopleFloor19 = new javax.swing.JTextField();
        jTextFieldPeopleFloor20 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldPeopleFloor0 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("HOSPITAL ELEVATORS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Elevator 1");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Elevator 2");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Elevator 3");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Floor");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("People counter");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setText("Destinations");

        jTextFieldFloorElevator1.setEditable(false);

        jTextFieldFloorElevator2.setEditable(false);

        jTextFieldFloorElevator3.setEditable(false);

        jTextFieldPeopleCounterElevator2.setEditable(false);

        jTextFieldDestinationsElevator1.setEditable(false);

        jTextFieldPeopleCounterElevator3.setEditable(false);

        jButton1.setBackground(new java.awt.Color(255, 0, 51));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("EVACUATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextFieldPeopleCounterElevator1.setEditable(false);

        jTextFieldDestinationsElevator2.setEditable(false);

        jTextFieldDestinationsElevator3.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(268, 268, 268)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(177, 177, 177)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldFloorElevator1)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldPeopleCounterElevator1))
                                .addGap(211, 211, 211)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldFloorElevator2)
                                    .addComponent(jTextFieldPeopleCounterElevator2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(215, 215, 215)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldFloorElevator3)
                                    .addComponent(jTextFieldPeopleCounterElevator3)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(jTextFieldDestinationsElevator1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jTextFieldDestinationsElevator2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jTextFieldDestinationsElevator3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(33, 33, 33)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldFloorElevator1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldFloorElevator2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldFloorElevator3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldPeopleCounterElevator1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleCounterElevator2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleCounterElevator3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldDestinationsElevator1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDestinationsElevator2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDestinationsElevator3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("Floor 1");

        jLabel9.setText("Floor 2");

        jLabel13.setText("Floor 3");

        jLabel14.setText("Floor 4");

        jLabel15.setText("Floor 5");

        jLabel16.setText("Floor 6");

        jLabel17.setText("Floor 7");

        jLabel18.setText("Floor 8");

        jLabel19.setText("Floor 9");

        jLabel20.setText("Floor 10");

        jLabel21.setText("Floor 11");

        jLabel22.setText("Floor 12");

        jLabel23.setText("Floor 13");

        jLabel24.setText("Floor 14");

        jLabel25.setText("Floor 15");

        jLabel26.setText("Floor 16");

        jLabel27.setText("Floor 17");

        jLabel28.setText("Floor 18");

        jLabel29.setText("Floor 19");

        jLabel30.setText("Floor 20");

        jTextFieldPeopleFloor2.setEditable(false);

        jTextFieldPeopleFloor1.setEditable(false);

        jTextFieldPeopleFloor3.setEditable(false);

        jTextFieldPeopleFloor4.setEditable(false);

        jTextFieldPeopleFloor5.setEditable(false);

        jTextFieldPeopleFloor6.setEditable(false);

        jTextFieldPeopleFloor7.setEditable(false);

        jTextFieldPeopleFloor8.setEditable(false);

        jTextFieldPeopleFloor9.setEditable(false);

        jTextFieldPeopleFloor10.setEditable(false);

        jTextFieldPeopleFloor11.setEditable(false);

        jTextFieldPeopleFloor12.setEditable(false);

        jTextFieldPeopleFloor13.setEditable(false);

        jTextFieldPeopleFloor14.setEditable(false);

        jTextFieldPeopleFloor15.setEditable(false);

        jTextFieldPeopleFloor16.setEditable(false);

        jTextFieldPeopleFloor17.setEditable(false);

        jTextFieldPeopleFloor18.setEditable(false);

        jTextFieldPeopleFloor19.setEditable(false);

        jTextFieldPeopleFloor20.setEditable(false);

        jLabel10.setText("Floor 0");

        jTextFieldPeopleFloor0.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setText("People on floor");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldPeopleFloor4, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(45, 45, 45)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldPeopleFloor3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldPeopleFloor1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldPeopleFloor2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPeopleFloor0, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPeopleFloor5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPeopleFloor10, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextFieldPeopleFloor12, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jTextFieldPeopleFloor11)
                    .addComponent(jTextFieldPeopleFloor13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPeopleFloor14, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPeopleFloor15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldPeopleFloor19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jTextFieldPeopleFloor18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldPeopleFloor17, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldPeopleFloor16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldPeopleFloor20))
                .addGap(27, 27, 27))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPeopleFloor1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel16)
                    .addComponent(jLabel21)
                    .addComponent(jLabel26)
                    .addComponent(jTextFieldPeopleFloor6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPeopleFloor2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel17)
                            .addComponent(jLabel22)
                            .addComponent(jLabel27)
                            .addComponent(jTextFieldPeopleFloor7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPeopleFloor12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPeopleFloor17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextFieldPeopleFloor3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel23)
                            .addComponent(jLabel28)
                            .addComponent(jTextFieldPeopleFloor8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPeopleFloor13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPeopleFloor18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPeopleFloor4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel24)
                        .addComponent(jLabel29)
                        .addComponent(jTextFieldPeopleFloor9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldPeopleFloor14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldPeopleFloor19, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextFieldPeopleFloor5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel25)
                    .addComponent(jLabel30)
                    .addComponent(jTextFieldPeopleFloor10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPeopleFloor20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldPeopleFloor0, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            this.controller.evacuateSystem();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    void setElevatorsInfo(List<ElevatorInfoDTO> elevatorsInfos) {
        for (ElevatorInfoDTO elevatorInfoDto : elevatorsInfos) {
            if (elevatorInfoDto.getIdentification().equals("elevator_0")) {
                this.jTextFieldFloorElevator1.setText(elevatorInfoDto.getFloorAsString());
                this.jTextFieldPeopleCounterElevator1.setText(elevatorInfoDto.getPeopleCounterAsString());
                this.jTextFieldDestinationsElevator1.setText(elevatorInfoDto.getPeopleDestinationsAsString());

            } else if (elevatorInfoDto.getIdentification().equals("elevator_1")) {
                this.jTextFieldFloorElevator2.setText(elevatorInfoDto.getFloorAsString());
                this.jTextFieldPeopleCounterElevator2.setText(elevatorInfoDto.getPeopleCounterAsString());
                this.jTextFieldDestinationsElevator2.setText(elevatorInfoDto.getPeopleDestinationsAsString());

            } else if (elevatorInfoDto.getIdentification().equals("elevator_bkp")) {
                this.jTextFieldFloorElevator3.setText(elevatorInfoDto.getFloorAsString());
                this.jTextFieldPeopleCounterElevator3.setText(elevatorInfoDto.getPeopleCounterAsString());
                this.jTextFieldDestinationsElevator3.setText(elevatorInfoDto.getPeopleDestinationsAsString());

            }
        }
    }

    void setPeopleInFloor(Map<Integer, Integer> mapOfPeople) {
        this.jTextFieldPeopleFloor0.setText(String.valueOf(mapOfPeople.get(0)));
        this.jTextFieldPeopleFloor1.setText(String.valueOf(mapOfPeople.get(1)));
        this.jTextFieldPeopleFloor2.setText(String.valueOf(mapOfPeople.get(2)));
        this.jTextFieldPeopleFloor3.setText(String.valueOf(mapOfPeople.get(3)));
        this.jTextFieldPeopleFloor4.setText(String.valueOf(mapOfPeople.get(4)));
        this.jTextFieldPeopleFloor5.setText(String.valueOf(mapOfPeople.get(5)));
        this.jTextFieldPeopleFloor6.setText(String.valueOf(mapOfPeople.get(6)));
        this.jTextFieldPeopleFloor7.setText(String.valueOf(mapOfPeople.get(7)));
        this.jTextFieldPeopleFloor8.setText(String.valueOf(mapOfPeople.get(8)));
        this.jTextFieldPeopleFloor9.setText(String.valueOf(mapOfPeople.get(9)));
        this.jTextFieldPeopleFloor10.setText(String.valueOf(mapOfPeople.get(10)));
        this.jTextFieldPeopleFloor11.setText(String.valueOf(mapOfPeople.get(11)));
        this.jTextFieldPeopleFloor12.setText(String.valueOf(mapOfPeople.get(12)));
        this.jTextFieldPeopleFloor13.setText(String.valueOf(mapOfPeople.get(13)));
        this.jTextFieldPeopleFloor14.setText(String.valueOf(mapOfPeople.get(14)));
        this.jTextFieldPeopleFloor15.setText(String.valueOf(mapOfPeople.get(15)));
        this.jTextFieldPeopleFloor16.setText(String.valueOf(mapOfPeople.get(16)));
        this.jTextFieldPeopleFloor17.setText(String.valueOf(mapOfPeople.get(17)));
        this.jTextFieldPeopleFloor18.setText(String.valueOf(mapOfPeople.get(18)));
        this.jTextFieldPeopleFloor19.setText(String.valueOf(mapOfPeople.get(19)));
        this.jTextFieldPeopleFloor20.setText(String.valueOf(mapOfPeople.get(20)));
    }

    public ClientController getController() {
        return controller;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Front.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Front().setVisible(true);
                } catch (RemoteException | MalformedURLException | NotBoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldDestinationsElevator1;
    private javax.swing.JTextField jTextFieldDestinationsElevator2;
    private javax.swing.JTextField jTextFieldDestinationsElevator3;
    private javax.swing.JTextField jTextFieldFloorElevator1;
    private javax.swing.JTextField jTextFieldFloorElevator2;
    private javax.swing.JTextField jTextFieldFloorElevator3;
    private javax.swing.JTextField jTextFieldPeopleCounterElevator1;
    private javax.swing.JTextField jTextFieldPeopleCounterElevator2;
    private javax.swing.JTextField jTextFieldPeopleCounterElevator3;
    private javax.swing.JTextField jTextFieldPeopleFloor0;
    private javax.swing.JTextField jTextFieldPeopleFloor1;
    private javax.swing.JTextField jTextFieldPeopleFloor10;
    private javax.swing.JTextField jTextFieldPeopleFloor11;
    private javax.swing.JTextField jTextFieldPeopleFloor12;
    private javax.swing.JTextField jTextFieldPeopleFloor13;
    private javax.swing.JTextField jTextFieldPeopleFloor14;
    private javax.swing.JTextField jTextFieldPeopleFloor15;
    private javax.swing.JTextField jTextFieldPeopleFloor16;
    private javax.swing.JTextField jTextFieldPeopleFloor17;
    private javax.swing.JTextField jTextFieldPeopleFloor18;
    private javax.swing.JTextField jTextFieldPeopleFloor19;
    private javax.swing.JTextField jTextFieldPeopleFloor2;
    private javax.swing.JTextField jTextFieldPeopleFloor20;
    private javax.swing.JTextField jTextFieldPeopleFloor3;
    private javax.swing.JTextField jTextFieldPeopleFloor4;
    private javax.swing.JTextField jTextFieldPeopleFloor5;
    private javax.swing.JTextField jTextFieldPeopleFloor6;
    private javax.swing.JTextField jTextFieldPeopleFloor7;
    private javax.swing.JTextField jTextFieldPeopleFloor8;
    private javax.swing.JTextField jTextFieldPeopleFloor9;
    // End of variables declaration//GEN-END:variables
}
