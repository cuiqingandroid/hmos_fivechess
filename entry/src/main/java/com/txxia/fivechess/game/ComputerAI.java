package com.txxia.fivechess.game;


public class ComputerAI {

    private static int BLACK = 1;
    private static int WHITE = 2;

    private int mWidth;
    private int mHeight;

    // Black chess priority value array
    private int[][][] black;
    // white chess priority value array
    private int[][][] white;

    // 五子棋中的各个点的权值
    private int[][] plaValue = {
            {2, 6, 173, 212, 250, 250, 250},
            {0, 5, 7, 200, 230, 231, 231},
            {0, 0, 0, 0, 230, 230, 230, 0}
    };
    private int[][] cpuValue = {
            {0, 3, 166, 186, 229, 229, 229},
            {0, 0, 5, 167, 220, 220, 220},
            {0, 0, 0, 0, 220, 220, 220, 0}
    };

    public ComputerAI(int width, int height) {
        mWidth = width;
        mHeight = height;
        black = new int[width][height][5];
        white = new int[width][height][5];
    }

    /**
     * 更新棋盘权值
     */
    public void updateValue(int[][] chessMap) {
        int[] currComputerValue = {0, 0, 0, 0};
        int[] currPlayerValue = {0, 0, 0, 0};
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                if (chessMap[i][j] == 0) {
                    int counter = 0;
                    // 纵向
                    for (int k = j + 1; k < mHeight; k++) {
                        if (chessMap[i][k] == BLACK)
                            currComputerValue[0]++;
                        if (chessMap[i][k] == 0)
                            break;
                        if (chessMap[i][k] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == mHeight - 1)
                            counter++;
                    }

                    for (int k = j - 1; k >= 0; k--) {
                        if (chessMap[i][k] == BLACK)
                            currComputerValue[0]++;
                        if (chessMap[i][k] == 0)
                            break;
                        if (chessMap[i][k] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == 0)
                            counter++;
                    }
                    if (j == 0 || j == mHeight - 1)
                        counter++;
                    white[i][j][0] = cpuValue[counter][currComputerValue[0]];
                    currComputerValue[0] = 0;
                    counter = 0;

                    // 反斜线
                    for (int k = i + 1, l = j + 1; l < mHeight; k++, l++) {
                        if (k >= mHeight) {
                            break;
                        }
                        if (chessMap[k][l] == BLACK)
                            currComputerValue[1]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == mWidth - 1 || l == mHeight - 1)
                            counter++;

                    }


                    for (int k = i - 1, l = j - 1; l >= 0; k--, l--) {
                        if (k < 0) {
                            break;
                        }
                        if (chessMap[k][l] == BLACK)
                            currComputerValue[1]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == 0 || l == 0)
                            counter++;

                    }
                    if (i == 0 || i == mWidth - 1 || j == 0 || j == mHeight - 1)
                        counter++;

                    white[i][j][1] = cpuValue[counter][currComputerValue[1]];
                    currComputerValue[1] = 0;
                    counter = 0;

                    // 横向
                    for (int k = i + 1; k < mWidth; k++) {

                        if (chessMap[k][j] == BLACK)
                            currComputerValue[2]++;
                        if (chessMap[k][j] == 0)
                            break;
                        if (chessMap[k][j] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == mWidth - 1)
                            counter++;
                    }


                    for (int k = i - 1; k >= 0; k--) {

                        if (chessMap[k][j] == BLACK)
                            currComputerValue[2]++;
                        if (chessMap[k][j] == 0)
                            break;
                        if (chessMap[k][j] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == 0)
                            counter++;
                    }

                    if (i == 0 || i == mWidth - 1)
                        counter++;
                    white[i][j][2] = cpuValue[counter][currComputerValue[2]];
                    currComputerValue[2] = 0;
                    counter = 0;

                    // 正斜线
                    for (int k = i - 1, l = j + 1; l < mWidth; k--, l++) {

                        if (k < 0) {
                            break;
                        }
                        if (chessMap[k][l] == BLACK)
                            currComputerValue[3]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == 0 || l == mHeight - 1)
                            counter++;

                    }


                    for (int k = i + 1, l = j - 1; l >= 0; k++, l--) {

                        if (k >= mWidth) {
                            break;
                        }
                        if (chessMap[k][l] == BLACK)
                            currComputerValue[3]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == WHITE) {
                            counter++;
                            break;
                        }
                        if (k == mWidth - 1 || l == 0)
                            counter++;

                    }
                    if (i == 0 || i == mWidth - 1 || j == 0 || j == mHeight - 1)
                        counter++;
                    white[i][j][3] = cpuValue[counter][currComputerValue[3]];
                    currComputerValue[3] = 0;
                    counter = 0;

                    // 同时判断两个方向上的权值，并给他一个适当的权值
                    for (int k = 0; k < 4; k++) {
                        if (white[i][j][k] == 173)
                            counter++;
                    }
                    if (counter >= 2 && white[i][j][4] < 175)
                        white[i][j][4] = 175;

                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (white[i][j][k] == 173 && white[i][j][l] == 200
                                    && white[i][j][4] < 201)
                                white[i][j][4] = 201;
                        }

                    }

                    if (j >= 1) {
                        if (chessMap[i][j - 1] == 0) {
                            if (white[i][j - 1][0] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            // 如果两个方向上的权值都是活三，降低权值
                            if (white[i][j - 1][0] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }
                        }

                    }

                    if (j >= 1 && i >= 1) {
                        if (chessMap[i - 1][j - 1] == 0) {
                            if (white[i - 1][j - 1][1] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i - 1][j - 1][1] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i >= 1) {
                        if (chessMap[i - 1][j] == 0) {
                            if (white[i - 1][j][2] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i - 1][j][2] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i > 0 && j < mHeight - 1) {
                        if (chessMap[i - 1][j + 1] == 0) {
                            if (white[i - 1][j + 1][3] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i - 1][j + 1][3] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (j < mHeight - 1) {
                        if (chessMap[i][j + 1] == 0) {
                            if (white[i][j + 1][0] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i][j + 1][0] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }

                        }
                    }

                    if (i < mWidth - 1 && j < mHeight - 1) {
                        if (chessMap[i + 1][j + 1] == 0) {
                            if (white[i + 1][j + 1][1] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i + 1][j + 1][1] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i < mWidth - 1) {
                        if (chessMap[i + 1][j] == 0) {
                            if (white[i + 1][j][2] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i + 1][j][2] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i < mWidth - 1 && j > 0) {
                        if (chessMap[i + 1][j - 1] == 0) {
                            if (white[i + 1][j - 1][3] >= 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] >= 173) {
                                        if (white[i][j][4] < 201) {
                                            white[i][j][4] = 201;
                                        }
                                    }
                                }
                            }
                            if (white[i + 1][j - 1][3] == 173) {
                                for (int k = 0; k < 4; k++) {
                                    if (white[i][j][k] == 173) {
                                        if (white[i][j][4] == 201) {
                                            white[i][j][4] = 175;
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                if (chessMap[i][j] == 0) {
                    int counter = 0;
                    for (int k = j + 1; k < mHeight; k++) {

                        if (chessMap[i][k] == WHITE)
                            currPlayerValue[0]++;
                        if (chessMap[i][k] == 0)
                            break;
                        if (chessMap[i][k] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == mHeight - 1)
                            counter++;
                    }


                    for (int k = j - 1; k >= 0; k--) {

                        if (chessMap[i][k] == WHITE)
                            currPlayerValue[0]++;
                        if (chessMap[i][k] == 0)
                            break;
                        if (chessMap[i][k] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == 0)
                            counter++;
                    }
                    if (j == 0 || j == mHeight - 1)
                        counter++;
                    black[i][j][0] = plaValue[counter][currPlayerValue[0]];
                    currPlayerValue[0] = 0;
                    counter = 0;

                    for (int k = i + 1, l = j + 1; l < mHeight; k++, l++) {
                        if (k >= mWidth) {
                            break;
                        }
                        if (chessMap[k][l] == WHITE)
                            currPlayerValue[1]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == mWidth - 1 || l == mHeight - 1)
                            counter++;

                    }


                    for (int k = i - 1, l = j - 1; l >= 0; k--, l--) {

                        if (k < 0) {
                            break;
                        }
                        if (chessMap[k][l] == WHITE)
                            currPlayerValue[1]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == 0 || l == 0)
                            counter++;

                    }
                    if (i == 0 || i == mWidth - 1 || j == 0 || j == mHeight - 1)
                        counter++;
                    black[i][j][1] = plaValue[counter][currPlayerValue[1]];
                    currPlayerValue[1] = 0;
                    counter = 0;

                    for (int k = i + 1; k < mWidth; k++) {

                        if (chessMap[k][j] == WHITE)
                            currPlayerValue[2]++;
                        if (chessMap[k][j] == 0)
                            break;
                        if (chessMap[k][j] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == mWidth - 1)
                            counter++;
                    }


                    for (int k = i - 1; k >= 0; k--) {

                        if (chessMap[k][j] == WHITE)
                            currPlayerValue[2]++;
                        if (chessMap[k][j] == 0)
                            break;
                        if (chessMap[k][j] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == 0)
                            counter++;
                    }
                    if (i == 0 || i == mWidth - 1)
                        counter++;
                    black[i][j][2] = plaValue[counter][currPlayerValue[2]];
                    currPlayerValue[2] = 0;
                    counter = 0;

                    for (int k = i - 1, l = j + 1; l < mHeight; k--, l++) {

                        if (k < 0) {
                            break;
                        }
                        if (chessMap[k][l] == WHITE)
                            currPlayerValue[3]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == 0 || l == mHeight - 1)
                            counter++;

                    }


                    for (int k = i + 1, l = j - 1; l >= 0; k++, l--) {

                        if (k >= mWidth) {
                            break;
                        }
                        if (chessMap[k][l] == WHITE)
                            currPlayerValue[3]++;
                        if (chessMap[k][l] == 0)
                            break;
                        if (chessMap[k][l] == BLACK) {
                            counter++;
                            break;
                        }
                        if (k == mWidth - 1 || l == 0)
                            counter++;

                    }
                    if (i == 0 || i == mWidth - 1 || j == 0 || j == mHeight - 1)
                        counter++;
                    black[i][j][3] = plaValue[counter][currPlayerValue[3]];
                    currPlayerValue[3] = 0;
                    counter = 0;

                    for (int k = 0; k < 4; k++) {
                        if (black[i][j][k] == 166)
                            counter++;
                    }
                    if (counter >= 2 && black[i][j][0] < 174) {
                        black[i][j][0] = 174;

                    }
                    counter = 0;

                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (black[i][j][k] == 166 && black[i][j][l] == 167
                                    && black[i][j][0] < 176)
                                black[i][j][0] = 176;
                        }
                    }

                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (black[i][j][k] == 166 && black[i][j][l] == 186
                                    && black[i][j][0] < 177)
                                black[i][j][0] = 177;
                        }
                    }

                    for (int k = 0; k < 4; k++) {
                        if (black[i][j][k] == 167)
                            counter++;
                    }
                    if (counter >= 2 && black[i][j][0] < 178)
                        black[i][j][0] = 178;
                    counter = 0;

                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (black[i][j][k] == 167 && black[i][j][l] == 186
                                    && black[i][j][0] < 179)
                                black[i][j][0] = 179;
                        }
                    }

                    for (int k = 0; k < 4; k++) {
                        if (black[i][j][k] == 186)
                            counter++;
                    }
                    if (counter >= 2 && black[i][j][0] < 180)
                        black[i][j][0] = 180;

                    if (j >= 1) {
                        if (chessMap[i][j - 1] == 0) {
                            if (black[i][j - 1][0] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166 && black[i][j][k] < 176) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }

                    }

                    if (j >= 1 && i >= 1) {
                        if (chessMap[i - 1][j - 1] == 0) {
                            if (black[i - 1][j - 1][1] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i >= 1) {
                        if (chessMap[i - 1][j] == 0) {
                            if (black[i - 1][j][2] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i > 0 && j < mHeight - 1) {
                        if (chessMap[i - 1][j + 1] == 0) {
                            if (black[i - 1][j + 1][3] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (j < mHeight - 1) {
                        if (chessMap[i][j + 1] == 0) {
                            if (black[i][j + 1][0] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i < mWidth - 1 && j < mHeight - 1) {
                        if (chessMap[i + 1][j + 1] == 0) {
                            if (black[i + 1][j + 1][1] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i < mWidth - 1) {
                        if (chessMap[i + 1][j] == 0) {
                            if (black[i + 1][j][2] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (i < mWidth - 1 && j > 0) {
                        if (chessMap[i + 1][j - 1] == 0) {
                            if (black[i + 1][j - 1][3] >= 166) {
                                for (int k = 0; k < 4; k++) {
                                    if (black[i][j][k] >= 166) {
                                        if (black[i][j][0] < 176) {
                                            black[i][j][0] = 176;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public Coordinate getNextCoordinate(int[][] map) {
        int maxpSum = 0;
        int maxcSum = 0;

        int maxpValue = -10;
        int maxcValue = -10;
        int blackRow = 0;
        int blackCollum = 0;
        int whiteRow = 0;
        int whiteCollum = 0;
        for (int i = 0; i < mWidth; i++) {
            for (int j = 0; j < mHeight; j++) {
                if (map[i][j] == 0) {
                    for (int k = 0; k < 4; k++) {
                        if (black[i][j][k] > maxpValue) {
                            blackRow = i;
                            blackCollum = j;
                            maxpValue = black[i][j][k];
                            maxpSum = black[i][j][0] + black[i][j][1]
                                    + black[i][j][2] + black[i][j][3];
                        }

                        if (black[i][j][k] == maxpValue) {
                            if (maxpSum < (black[i][j][0] + black[i][j][1]
                                    + black[i][j][2] + black[i][j][3])) {
                                blackRow = i;
                                blackCollum = j;
                                maxpSum = black[i][j][0] + black[i][j][1]
                                        + black[i][j][2] + black[i][j][3];
                            }
                        }

                        if (white[i][j][k] > maxcValue) {
                            whiteRow = i;
                            whiteCollum = j;
                            maxcValue = white[i][j][k];
                            maxcSum = black[i][j][0] + black[i][j][1]
                                    + black[i][j][2] + black[i][j][3];

                        }

                        if (white[i][j][k] == maxcValue) {
                            if (maxcSum < (black[i][j][0] + black[i][j][1]
                                    + black[i][j][2] + black[i][j][3])) {
                                whiteRow = i;
                                whiteCollum = j;
                                maxcSum = black[i][j][0] + black[i][j][1]
                                        + black[i][j][2] + black[i][j][3];
                            }
                        }

                    }
                }

            }
        }
        Coordinate c = new Coordinate();
        if (maxcValue > maxpValue) {
            c.x = whiteRow;
            c.y = whiteCollum;
        } else {
            c.x = blackRow;
            c.y = blackCollum;
        }
        return c;
    }
}
