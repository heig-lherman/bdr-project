package ddl;

import heig.bdr.choochoo.business.model.*;
import heig.bdr.choochoo.business.model.locality.*;
import heig.bdr.choochoo.business.model.reference.StationType;
import heig.bdr.choochoo.business.model.reference.TrackType;
import heig.bdr.choochoo.business.model.user.Team;
import heig.bdr.choochoo.business.model.user.User;
import heig.bdr.choochoo.business.model.user.UserToken;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

public class DDLGenerator {

    @Test
    void generateDDL() {
        var metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                        .applySetting("hibernate.temp.use_jdbc_metadata_defaults", false)
                        .build()
        ).addAnnotatedClasses(
                Canton.class,
                Country.class,
                Locality.class,
                ForeignLocality.class,
                SwissLocality.class,
                StationType.class,
                TrackType.class,
                User.class,
                UserToken.class,
                Team.class,
                Journey.class,
                Line.class,
                Segment.class,
                Station.class,
                LineAssessment.class
        ).buildMetadata();

        deleteFile("sql/ddl/create.sql");
        deleteFile("sql/ddl/drop.sql");

        var schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("sql/ddl/create.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
        schemaExport.setOutputFile("sql/ddl/drop.sql");
        schemaExport.drop(EnumSet.of(TargetType.SCRIPT), metadata);
    }

    private void deleteFile(String path) {
        try {
            Files.delete(Path.of(path));
        } catch (Exception ignored) {
        }
    }
}
