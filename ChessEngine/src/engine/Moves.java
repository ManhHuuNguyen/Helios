package engine;

public class Moves {

    static long NOT_WHITE_PIECES;
    static long BLACK_PIECES;
    static long NOT_BLACK_PIECES;
    static long WHITE_PIECES;
    static long EMPTY;
    static long OCCUPIED;
    private static final long[] columns ={72340172838076673L, 144680345676153346L, 289360691352306692L, 578721382704613384L,
                            1157442765409226768L, 2314885530818453536L, 4629771061636907072L, -9187201950435737472L};
    private static final long[] rows = {255L, 65280L, 16711680L, 4278190080L, 1095216660480L,
                          280375465082880L, 71776119061217280L, -72057594037927936L};

    // diagonal "\"starting from square 7
    private static final long[] LRDiagonals ={128L, 32832L, 8405024L, 2151686160L, 550831656968L, 141012904183812L, 36099303471055874L,
            -9205322385119247871L, 4620710844295151872L, 2310355422147575808L, 1155177711073755136L, 577588855528488960L,
            288794425616760832L, 144396663052566528L, 72057594037927936L};
    // diagonal "/" starting from square 0
    private static final long[] RLDiagonals={1L, 258L, 66052L, 16909320L, 4328785936L, 1108169199648L,
            283691315109952L, 72624976668147840L, 145249953336295424L, 290499906672525312L, 580999813328273408L,
            1161999622361579520L, 2323998145211531264L, 4647714815446351872L, -9223372036854775808L};

    private static final long KnightSpan=11333835722063872L;
    private static final long KingSpan=61814108848128L;
    private static final long WQside = 2017612633061982208L;
    private static final long WKside = 8070450532247928832L;
    private static final long BKside = 112L;
    private static final long BQside = 28L;

    public static long CrossMoves(int position, long OCCUPIED){
       long s = 1L << position;
       long horizontalMoves = (((OCCUPIED&rows[position/8]) - 2*s)&rows[position/8])^
               ((Long.reverse(Long.reverse(OCCUPIED&rows[position/8])-2*Long.reverse(s)))&rows[position/8]);

       long verticalMoves = (((OCCUPIED&columns[position%8])-2*s)&columns[position%8])^
               (Long.reverse(Long.reverse(OCCUPIED&columns[position%8])-2*Long.reverse(s))&columns[position%8]);
       return horizontalMoves ^ verticalMoves;
    }

    public static long DiagonalMoves(int position, long OCCUPIED){
        long s = 1L << position;
        int index1 = position /8 + 7 - (position%8);
        int index2 = position /8 + (position%8);

        long diagonals1 = (((OCCUPIED&LRDiagonals[index1]) - 2*s)&LRDiagonals[index1])^
                ((Long.reverse(Long.reverse(OCCUPIED&LRDiagonals[index1])-2*Long.reverse(s)))&LRDiagonals[index1]);

        long diagonals2 = (((OCCUPIED&RLDiagonals[index2])-2*s)&RLDiagonals[index2])^
                (Long.reverse(Long.reverse(OCCUPIED&RLDiagonals[index2])-2*Long.reverse(s))&RLDiagonals[index2]);
        return diagonals1 ^ diagonals2;
    }

    public static String NpossibleMoves(long N, long NOT_MY_PIECES){
        String moveString = "";
        long allKnights = N & ~(N-1);
        long NPP;
        while (allKnights != 0){
            int KnightPos = Long.numberOfTrailingZeros(allKnights);

            if (KnightPos > 36) NPP = KnightSpan << (KnightPos-36);
            else NPP = KnightSpan >> (36-KnightPos);

            if (KnightPos%8<2) NPP &= ~(columns[6]|columns[7]);
            else if (KnightPos%8>5) NPP &= ~(columns[0]|columns[1]);

            NPP &= NOT_MY_PIECES;
            long possibility = NPP & ~(NPP-1);
            while (possibility !=0){
                int newPos = Long.numberOfTrailingZeros(possibility);
                moveString += (KnightPos/8) +""+ (KnightPos%8) +""+ (newPos/8) +""+ (newPos%8);
                NPP &= ~possibility;
                possibility = NPP & ~(NPP-1);
            }
            N &= ~allKnights;
            allKnights = N & ~(N-1);
        }
        return moveString;

    }

    public static String QpossibleMoves(long Q, long OCCUPIED, long NOT_MY_PIECES){
        String moveString = "";
        long allQueens = Q & ~(Q-1);
        long QPP;
        while (allQueens != 0){
            int QueenPos = Long.numberOfTrailingZeros(allQueens);
            QPP = (DiagonalMoves(QueenPos, OCCUPIED)&NOT_MY_PIECES)^(CrossMoves(QueenPos, OCCUPIED)&NOT_MY_PIECES);
            long possibility = QPP & ~(QPP-1);
            while (possibility !=0){
                int newPos = Long.numberOfTrailingZeros(possibility);
                moveString += (QueenPos/8) +""+ (QueenPos%8) +""+ (newPos/8) +""+ (newPos%8);
                QPP &= ~possibility;
                possibility = QPP & ~(QPP-1);
            }
            Q &= ~allQueens;
            allQueens = Q & ~(Q-1);
        }
        return moveString;

    }

    public static String BpossibleMoves(long B, long OCCUPIED, long NOT_MY_PIECES){
        String moveString = "";
        long allBishops = B & ~(B-1);
        long BPP;
        while (allBishops != 0){
            int BishopPos = Long.numberOfTrailingZeros(allBishops);
            BPP = DiagonalMoves(BishopPos, OCCUPIED)&NOT_MY_PIECES;
            long possibility = BPP & ~(BPP-1);
            while (possibility !=0){
                int newPos = Long.numberOfTrailingZeros(possibility);
                moveString += (BishopPos/8) +""+ (BishopPos%8) +""+ (newPos/8) +""+ (newPos%8);
                BPP &= ~possibility;
                possibility = BPP & ~(BPP-1);
            }
            B &= ~allBishops;
            allBishops = B & ~(B-1);
        }
        return moveString;
    }

    public static String RpossibleMoves(long R, long OCCUPIED, long NOT_MY_PIECES){
        String moveString = "";
        long allRooks = R & ~(R-1);
        long RPP;
        while (allRooks != 0){
            int rookPos = Long.numberOfTrailingZeros(allRooks);
            RPP = CrossMoves(rookPos, OCCUPIED)&NOT_MY_PIECES;
            long possibility = RPP & ~(RPP-1);
            while (possibility !=0){
                int newPos = Long.numberOfTrailingZeros(possibility);
                moveString += (rookPos/8) +""+ (rookPos%8) +""+ (newPos/8) +""+ (newPos%8);
                RPP &= ~possibility;
                possibility = RPP & ~(RPP-1);
            }
            R &= ~allRooks;
            allRooks = R & ~(R-1);
        }
        return moveString;
    }

    public static String WhitePossibleMoves(long[] boards, String history){

        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        NOT_WHITE_PIECES = ~(WK^WQ^WR^WB^WN^WP^BK);  // you can't capture black king
        BLACK_PIECES = BQ^BR^BB^BN^BP;
        OCCUPIED = WK^WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP;
        EMPTY = ~OCCUPIED;
        String allMoves ="";
        allMoves += WKpossibleMoves(boards) +
                NpossibleMoves(WN, NOT_WHITE_PIECES) +
                QpossibleMoves(WQ, OCCUPIED, NOT_WHITE_PIECES)+
                BpossibleMoves(WB, OCCUPIED, NOT_WHITE_PIECES) +
                WPpossibleMoves(WP, BP, EMPTY, BLACK_PIECES, history) +
                RpossibleMoves(WR, OCCUPIED, NOT_WHITE_PIECES);
        return allMoves;
    }

    public static String WKpossibleMoves(long[] boards){
        long WK = boards[0];
        String moveString = "";
        long KPP;
        int KingPos = Long.numberOfTrailingZeros(WK);
        if (KingPos > 36) KPP = KingSpan << (KingPos-36);
        else KPP = KingSpan >> (36-KingPos);

        if (KingPos%8<1) KPP &= ~columns[7];
        else if (KingPos%8>6) KPP &= ~columns[0];

        long dangerZone = WKdangerZone(boards);
        KPP &= NOT_WHITE_PIECES & ~dangerZone;
        long possibility = KPP & ~(KPP-1);
        while (possibility !=0){
            int newPos = Long.numberOfTrailingZeros(possibility);
            moveString += (KingPos/8) +""+ (KingPos%8) +""+ (newPos/8) +""+ (newPos%8);
            KPP &= ~possibility;
            possibility = KPP & ~(KPP-1);
        }
        // castle white king
        if (boards[12] != 0){
            if ((WKside&dangerZone)==0 && ((WKside^WK)&OCCUPIED)==0) moveString += "46 C";
        }
        // castle white queen
        if (boards[13] != 0){
            if ((WQside&dangerZone)==0 && ((WQside^WK|(1L<<57))&OCCUPIED)==0) moveString += "42 C";
        }
        return moveString;
    }

    public static long WKdangerZone(long[] boards) {
        long WK = boards[0]; long WQ = boards[1]; long WR = boards[2];
        long WB = boards[3]; long WN = boards[4]; long WP = boards[5];
        long BK = boards[6]; long BQ = boards[7]; long BR = boards[8];
        long BB = boards[9]; long BN = boards[10]; long BP = boards[11];

        long OCCUPIED = WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP; // no WK
        long dangerBoard;
        //King
        long KPP;
        int KingPos = Long.numberOfTrailingZeros(BK);
        if (KingPos > 36) KPP = KingSpan << (KingPos - 36);
        else KPP = KingSpan >> (36 - KingPos);
        if (KingPos % 8 < 1) KPP &= ~columns[7];
        else if (KingPos % 8 > 6) KPP &= ~columns[0];
        dangerBoard = KPP;
        //Knight
        long allKnights = BN & ~(BN - 1);
        long NPP;
        while (allKnights != 0) {
            int KnightPos = Long.numberOfTrailingZeros(allKnights);
            if (KnightPos > 36) NPP = KnightSpan << (KnightPos - 36);
            else NPP = KnightSpan >> (36 - KnightPos);
            if (KnightPos % 8 < 2) NPP &= ~(columns[6] | columns[7]);
            else if (KnightPos % 8 > 5) NPP &= ~(columns[0] | columns[1]);
            dangerBoard |= (NPP ^ (1L<<KnightPos));
            BN &= ~allKnights;
            allKnights = BN & ~(BN - 1);
        }
        //Queen/Rook cross threats
        long QR = BQ ^ BR;
        long allPieces = QR & ~(QR - 1);
        while (allPieces != 0){
            int pos = Long.numberOfTrailingZeros(QR);
            dangerBoard|= CrossMoves(pos, OCCUPIED);
            QR &= ~allPieces;
            allPieces = QR & ~(QR-1);
        }
        //Queen/Bishop diagonal threats
        long QB = BQ ^ BB;
        allPieces = QB & ~(QB - 1);
        while (allPieces != 0){
            int pos = Long.numberOfTrailingZeros(QB);
            dangerBoard|= DiagonalMoves(pos, OCCUPIED);
            QB &= ~allPieces;
            allPieces = QB & ~(QB-1);
        }
        //Pawn
        dangerBoard |= ((BP&(~columns[0]))<<7);
        dangerBoard |= ((BP&(~columns[7]))<<9);
        return dangerBoard;
    }


    public static String WPpossibleMoves(long WP, long BP, long EMPTY, long BLACK_PIECES, String history){

        String moveString = ""; //old row old column, new row new column

        // eat right
        long pawnMoves = ((WP&(~columns[7]))>>7)&(~rows[0])&BLACK_PIECES;
        long possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos/8+1) + "" + (pos%8-1) + "" + (pos/8) + "" + (pos%8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves-1);
        }

        // eat left
        pawnMoves = ((WP&(~columns[0]))>>9)&(~rows[0])&BLACK_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos/8+1) + "" + (pos%8+1) + "" + (pos/8) + "" + (pos%8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves-1);
        }

        // one move forward
        pawnMoves = (WP>>8)&(~rows[0])&EMPTY;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos / 8 + 1) + "" + (pos % 8) + "" + (pos / 8) + "" + (pos % 8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }

        // 2 move forward
        pawnMoves = (WP>>16)&EMPTY&(EMPTY>>8)&rows[4];
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos / 8 + 2) + "" + (pos % 8) + "" + (pos / 8) + "" + (pos % 8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }
        // pawn promotion. special layout: x1 x2 Promotion Type P  (bc y1=1, y2=0 is obvious)
        // eat right and promote
        pawnMoves = ((WP&(~columns[7]))>>7)&rows[0]&BLACK_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos%8-1)+""+(pos%8)+"QP" + (pos%8-1)+""+(pos%8)+"RP" + (pos%8-1)+""+(pos%8)+"BP" + (pos%8-1)+""+(pos%8)+"NP";
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }

        // eat left and promote
        pawnMoves = ((WP&(~columns[0]))>>9)&rows[0]&BLACK_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos%8+1)+""+(pos%8)+"QP" + (pos%8+1)+""+(pos%8)+"RP" + (pos%8+1)+""+(pos%8)+"BP" + (pos%8+1)+""+(pos%8)+"NP";
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }
        //move ahead and promote
        pawnMoves = (WP>>8)&EMPTY&rows[0];
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos%8)+""+(pos%8)+"QP" + (pos%8)+""+(pos%8)+"RP" + (pos%8)+""+(pos%8)+"BP" + (pos%8)+""+(pos%8)+"NP";
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }

        // en passant x1 x2 Space E (bc y1=3 y2=2 is obvious, enpassant only happens on certain rows)
        int lastIndex = history.length()-1;
        if (lastIndex >= 3){
            if ((history.charAt(lastIndex)==history.charAt(lastIndex-2))&&
                    ((history.charAt(lastIndex-3)-'0')+2==(history.charAt(lastIndex-1)-'0'))){
                int enpassantColumn = history.charAt(lastIndex)-'0';
                // en passant right
                possibility = ((((WP&(~columns[7]))<<1)&BP&rows[3]&columns[enpassantColumn])>>8)&EMPTY;
                if (possibility != 0) moveString += (enpassantColumn-1) + "" + enpassantColumn + " E";
                // en passant left
                possibility = ((((WP&(~columns[0]))>>1)&BP&rows[3]&columns[enpassantColumn])>>8)&EMPTY;
                if (possibility != 0) moveString += "" + (enpassantColumn+1) + "" + enpassantColumn + " E";
            }
        }
        return moveString;
    }

    public static String BlackPossibleMoves(long[] boards, String history){
        long WK=boards[0]; long WQ=boards[1]; long WR=boards[2];
        long WB=boards[3]; long WN=boards[4]; long WP=boards[5];
        long BK=boards[6]; long BQ=boards[7]; long BR=boards[8];
        long BB=boards[9]; long BN=boards[10]; long BP=boards[11];

        NOT_BLACK_PIECES = ~(BK^BQ^BR^BB^BN^BP^WK);
        WHITE_PIECES = WQ^WR^WB^WN^WP;
        OCCUPIED = WK^WQ^WR^WB^WN^WP^BK^BQ^BR^BB^BN^BP;
        EMPTY = ~OCCUPIED;
        String allMoves ="";
        allMoves += BKpossibleMoves(boards) +
                    BPpossibleMoves(BP, WP, EMPTY, WHITE_PIECES, history)+
                    QpossibleMoves(BQ, OCCUPIED, NOT_BLACK_PIECES)+
                    RpossibleMoves(BR, OCCUPIED, NOT_BLACK_PIECES)+
                    BpossibleMoves(BB, OCCUPIED, NOT_BLACK_PIECES)+
                    NpossibleMoves(BN, NOT_BLACK_PIECES);
        return allMoves;
    }

    public static String BKpossibleMoves(long[] boards){
        long BK = boards[6];
        String moveString = "";
        long KPP;
        int KingPos = Long.numberOfTrailingZeros(BK);
        if (KingPos > 36) KPP = KingSpan << (KingPos-36);
        else KPP = KingSpan >> (36-KingPos);

        if (KingPos%8<1) KPP &= ~columns[7];
        else if (KingPos%8>6) KPP &= ~columns[0];

        long dangerZone = BKdangerZone(boards);
        KPP &= NOT_BLACK_PIECES & ~dangerZone;
        long possibility = KPP & ~(KPP-1);
        while (possibility !=0){
            int newPos = Long.numberOfTrailingZeros(possibility);
            moveString += (KingPos/8) +""+ (KingPos%8) +""+ (newPos/8) +""+ (newPos%8);
            KPP &= ~possibility;
            possibility = KPP & ~(KPP-1);
        }
        // castle black king
        if (boards[14] != 0){
            if ((BKside&dangerZone)==0 && ((BKside^BK)&OCCUPIED)==0) moveString += "46bC";
        }
        // castle black queen
        if (boards[15] != 0){
            if ((BQside&dangerZone)==0 && ((BQside^BK|(1L<<1))&OCCUPIED)==0) moveString += "42bC";
        }
        return moveString;
    }

    public static long BKdangerZone(long[] boards) {
        long WK = boards[0]; long WQ = boards[1]; long WR = boards[2];
        long WB = boards[3]; long WN = boards[4]; long WP = boards[5];
        long BK = boards[6]; long BQ = boards[7]; long BR = boards[8];
        long BB = boards[9]; long BN = boards[10]; long BP = boards[11];

        long OCCUPIED = WK^WQ^WR^WB^WN^WP^BQ^BR^BB^BN^BP;// no BK
        long dangerBoard;
        //King
        long KPP;
        int KingPos = Long.numberOfTrailingZeros(WK);
        if (KingPos > 36) KPP = KingSpan << (KingPos - 36);
        else KPP = KingSpan >> (36 - KingPos);
        if (KingPos % 8 < 1) KPP &= ~columns[7];
        else if (KingPos % 8 > 6) KPP &= ~columns[0];
        dangerBoard = KPP;
        //Knight
        long allKnights = WN & ~(WN - 1);
        long NPP;
        while (allKnights != 0) {
            int KnightPos = Long.numberOfTrailingZeros(allKnights);
            if (KnightPos > 36) NPP = KnightSpan << (KnightPos - 36);
            else NPP = KnightSpan >> (36 - KnightPos);
            if (KnightPos % 8 < 2) NPP &= ~(columns[6] | columns[7]);
            else if (KnightPos % 8 > 5) NPP &= ~(columns[0] | columns[1]);
            dangerBoard |= (NPP ^ (1L<<KnightPos));
            WN &= ~allKnights;
            allKnights = WN & ~(WN - 1);
        }
        //Queen/Rook cross threats
        long QR = WQ ^ WR;
        long allPieces = QR & ~(QR - 1);
        while (allPieces != 0){
            int pos = Long.numberOfTrailingZeros(QR);
            dangerBoard|= CrossMoves(pos, OCCUPIED);
            QR &= ~allPieces;
            allPieces = QR & ~(QR-1);
        }
        //Queen/Bishop diagonal threats
        long QB = WQ ^ WB;
        allPieces = QB & ~(QB - 1);
        while (allPieces != 0){
            int pos = Long.numberOfTrailingZeros(QB);
            dangerBoard|= DiagonalMoves(pos, OCCUPIED);
            QB &= ~allPieces;
            allPieces = QB & ~(QB-1);
        }
        //Pawn
        dangerBoard |= ((WP&(~columns[7]))>>7);
        dangerBoard |= ((WP&(~columns[0]))>>9);
        return dangerBoard;
    }

    public static String BPpossibleMoves(long BP, long WP, long EMPTY, long WHITE_PIECES, String history){
        String moveString = ""; //old row old column, new row new column

        // eat right
        long pawnMoves = ((BP&(~columns[0]))<<7)&(~rows[7])&WHITE_PIECES;
        long possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos/8-1) + "" + (pos%8+1) + "" + (pos/8) + "" + (pos%8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves-1);
        }

        // eat left
        pawnMoves = ((BP&(~columns[7]))<<9)&(~rows[7])&WHITE_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0){
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos/8-1) + "" + (pos%8-1) + "" + (pos/8) + "" + (pos%8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves-1);
        }

        // one move forward
        pawnMoves = (BP<<8)&(~rows[7])&EMPTY;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos / 8 - 1) + "" + (pos % 8) + "" + (pos / 8) + "" + (pos % 8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }

        // 2 move forward
        pawnMoves = (BP<<16)&EMPTY&(EMPTY<<8)&rows[3];
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos / 8 - 2) + "" + (pos % 8) + "" + (pos / 8) + "" + (pos % 8);
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }
        // pawn promotion. special layout: x1 x2 Promotion Type P  (bc y1=1, y2=0 is obvious)
        // eat right and promote
        pawnMoves = ((BP&(~columns[0]))<<7)&rows[7]&WHITE_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos%8+1)+""+(pos%8)+"qP" + (pos%8+1)+""+(pos%8)+"rP" + (pos%8+1)+""+(pos%8)+"bP" + (pos%8+1)+""+(pos%8)+"nP";
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }

        // eat left and promote
        pawnMoves = ((BP&(~columns[7]))<<9)&rows[7]&WHITE_PIECES;
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos%8-1)+""+(pos%8)+"qP" + (pos%8-1)+""+(pos%8)+"rP" + (pos%8-1)+""+(pos%8)+"bP" + (pos%8-1)+""+(pos%8)+"nP";
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }
        //move ahead and promote
        pawnMoves = (BP<<8)&EMPTY&rows[7];
        possibility = pawnMoves & ~(pawnMoves-1);
        while (possibility != 0) {
            int pos = Long.numberOfTrailingZeros(pawnMoves);
            moveString += (pos%8)+""+(pos%8)+"qP" + (pos%8)+""+(pos%8)+"rP" + (pos%8)+""+(pos%8)+"bP" + (pos%8)+""+(pos%8)+"nP";
            pawnMoves &= ~possibility;
            possibility = pawnMoves & ~(pawnMoves - 1);
        }

        // en passant x1 x2 Space E (bc y1=3 y2=2 is obvious, enpassant only happens on certain rows)
        int lastIndex = history.length()-1;
        if (lastIndex >= 3){
            if ((history.charAt(lastIndex)==history.charAt(lastIndex-2))&&
                    ((history.charAt(lastIndex-3)-'0')-2==(history.charAt(lastIndex-1)-'0'))){
                int enpassantColumn = history.charAt(lastIndex)-'0';
                // en passant right
                possibility = ((((BP&(~columns[0]))>>1)&WP&rows[4]&columns[enpassantColumn])<<8)&EMPTY;
                if (possibility != 0) moveString += (enpassantColumn+1) + "" + enpassantColumn + "bE";
                // en passant left
                possibility = ((((BP&(~columns[7]))<<1)&WP&rows[4]&columns[enpassantColumn])<<8)&EMPTY;
                if (possibility != 0) moveString += "" + (enpassantColumn-1) + "" + enpassantColumn + "bE";
            }
        }
        return moveString;
    }
}
