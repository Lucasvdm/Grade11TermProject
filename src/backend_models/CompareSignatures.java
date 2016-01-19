package backend_models;

public class CompareSignatures {

    //Moe stands for "margin of error", the greater moe is the less strict the comparison will be
    public static double moe = 3;
    public static double ratioMoe = 0.333;

    //
    public static boolean compareSignatures(int rows, int columns, int[] croppedDrawn, int[] croppedSaved) {
        boolean match = false;
        int[] drawnRowLines = AnalyzeSignatures.calculateRows(Scratchpad.scratchpad, rows, croppedDrawn);
        int[] drawnColumnLines = AnalyzeSignatures.calculateColumns(Scratchpad.scratchpad, columns, croppedDrawn);
        int[] savedRowLines = AnalyzeSignatures.calculateRows(Scratchpad.comparison, rows, croppedSaved);
        int[] savedColumnLines = AnalyzeSignatures.calculateColumns(Scratchpad.comparison, columns, croppedSaved);
        double drawnWHRatio = AnalyzeSignatures.calculateRatio(Scratchpad.scratchpad, croppedDrawn);
        double comparisonWHRatio = AnalyzeSignatures.calculateRatio(Scratchpad.comparison, croppedSaved);
        int maxScore = 0;
        
        //As the comparison proceeds, the scores for each category (rows, columns, etc.) are
        //set to the maximum possible value and then decreased whenever they exceed the
        //acceptable margin of error
        int rowScore = rows*2;
        maxScore += rowScore;
        
        for (int count = 0; count < rows; count++) {
            if (drawnRowLines[count] > (savedRowLines[count] + moe)) {
                rowScore = rowScore - 1;
            }
            if (drawnRowLines[count] < (savedRowLines[count] - moe)) {
                rowScore = rowScore - 1;
            }
        }

        int columnScore = columns*2;
        maxScore += columnScore;
        
        for (int count = 0; count < columns; count++) {
            if (drawnColumnLines[count] > (savedColumnLines[count] + moe)) {
                columnScore = columnScore - 1;
            }
            if (drawnColumnLines[count] < (savedColumnLines[count] - moe)) {
                columnScore = columnScore - 1;
            }
        }

        int ratioScore = rows + columns;
        maxScore += ratioScore;
        
        if(drawnWHRatio > comparisonWHRatio + ratioMoe){
            ratioScore = 0;
        }
        if(drawnWHRatio < comparisonWHRatio - ratioMoe){
            ratioScore = 0;
        }
        
        int totalScore = rowScore + columnScore + ratioScore;
        
        //If after the comparison is done the total score is still greater than 75% of the
        //maximum possible score, the signatures are considered to be a match
        if(totalScore > (maxScore*0.75)){
            match = true;
        }
        
        return match;
    }
}
