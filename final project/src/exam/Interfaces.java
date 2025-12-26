package exam;

interface AutoGradable { double grade(Answer answer); }
interface PartialCredit { double calculatePartialScore(Answer answer); }
interface Randomizable { void shuffle(); }