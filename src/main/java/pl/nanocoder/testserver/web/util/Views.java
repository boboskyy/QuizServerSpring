package pl.nanocoder.testserver.web.util;

public class Views {

   //Person
   public static class PeopleAll { }
   public static class PersonDetails extends PeopleAll{}

   public static class QuizStudent {}
   public static class QuizTeacherPublic extends QuizStudent {}
   public static class QuizTeacherPrivate extends QuizTeacherPublic {}
}
