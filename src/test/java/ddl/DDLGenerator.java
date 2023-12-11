package ddl;

import heig.bdr.choochoo.business.model.Journey;
import heig.bdr.choochoo.business.model.Line;
import heig.bdr.choochoo.business.model.LineAssessment;
import heig.bdr.choochoo.business.model.Segment;
import heig.bdr.choochoo.business.model.Station;
import heig.bdr.choochoo.business.model.locality.Canton;
import heig.bdr.choochoo.business.model.locality.Country;
import heig.bdr.choochoo.business.model.locality.ForeignLocality;
import heig.bdr.choochoo.business.model.locality.Locality;
import heig.bdr.choochoo.business.model.locality.SwissLocality;
import heig.bdr.choochoo.business.model.reference.StationType;
import heig.bdr.choochoo.business.model.reference.TrackType;
import heig.bdr.choochoo.business.model.user.Group;
import heig.bdr.choochoo.business.model.user.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import lombok.SneakyThrows;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.junit.jupiter.api.Test;

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
                Group.class,
                Journey.class,
                Line.class,
                Segment.class,
                Station.class,
                LineAssessment.class
        ).buildMetadata();

        deleteFile("sql/ddl/create.sql");

        var schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("sql/ddl/create.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
    }

    @SneakyThrows
    private void deleteFile(String path) {
        Files.delete(Path.of(path));
    }
}
