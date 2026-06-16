package servermagic.db.migrations;

import java.util.List;

import servermagic.db.BaseMigration;

public class ConfigAddSuperusers extends BaseMigration {

    @Override
    public String getName() {
        return "ConfigAddSuperusers";
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "ConfigInit" };
    }

    @Override
    public List<String> getSql() {
        return List.of(
                "ALTER TABLE config ADD COLUMN superusers TEXT NOT NULL DEFAULT '[\"coryo12345\"]';"
        );
    }

}
