module persistence {
    requires java.persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    requires org.hibernate.orm.core;

    opens com.tobiasekman.db to org.hibernate.orm.core;

    exports com.tobiasekman.db;
}