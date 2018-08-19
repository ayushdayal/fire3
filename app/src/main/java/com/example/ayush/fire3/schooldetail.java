package com.example.ayush.fire3;

class schooldetail{
        private String schoolname;
        private String section;
        private String classname;
        private String rollno;
        private String studentid;

        public schooldetail(String schoolname, String section, String classname, String rollno, String studentid) {
            this.schoolname = schoolname;
            this.section = section;
            this.classname = classname;
            this.rollno = rollno;
            this.studentid = studentid;
        }

        public schooldetail() {
        }

    public String getSchoolname() {
        return schoolname;
    }

    public String getSection() {
        return section;
    }

    public String getClassname() {
        return classname;
    }

    public String getRollno() {
        return rollno;
    }

    public String getStudentid() {
        return studentid;
    }
}
