/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

public class Matrix {
        private int cellSize;
        private int matrixSize;
        private Cell matrix[][];
        private int nKnockedOut; // number of cells "nulled out"

        public Matrix(int matrixSize, int cellSize) {
                this.matrixSize = matrixSize;
                this.cellSize = cellSize;
                matrix = new Cell[matrixSize][matrixSize];
                populate();
        }

        public void populate() {
                for (int i = 0; i < matrixSize ; i++) {
                        for( int j = 0 ; j < matrixSize ; j++) {
                                matrix[i][j] = new Cell(cellSize, i);
                        }
                }
        }

        public int returnArrayBound() {
                return matrixSize - 1;
        }

        public synchronized void clear(int i, int j) {
                matrix[i][j] = null;
                ++nKnockedOut;
        }

        public synchronized void repopulate(int i, int j) {
                matrix[i][j] = new Cell(cellSize, i + j);
                --nKnockedOut;
        }

        public synchronized void resetCellCount() {
                nKnockedOut = 0;
        }

        public synchronized int getCellCount() {
                return nKnockedOut;
        }
}
