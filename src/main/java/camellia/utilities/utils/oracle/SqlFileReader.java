package camellia.utilities.utils.oracle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SqlFileReader {
    public static void main(String[] args) {
        String inputFilePath = "C:\\Users\\24211\\Desktop\\ExportData\\soar_struct.sql"; // 输入文件路径
        String outputFilePath = "C:\\Users\\24211\\Desktop\\ExportData\\processed_soar_struct.sql"; // 输出文件路径

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                // 如果包含 "prompt"，替换为空字符串
                if (line.contains("prompt")) {
                    line = "";  // 删除整个 "prompt" 行
                }

                // 判断是否包含 "create"（忽略大小写），替换 "LYGZB_SOAR" 为 "PRHOUSE"
                if (line.toLowerCase().contains("create".toLowerCase())) {
                    line = line.replaceAll("(?i)LYGZB_SOAR", "PRHOUSE");  // 替换表名部分

                    // 如果是 "create table" 语句，生成 DROP TABLE 语句
                    if (line.toLowerCase().contains("create table")) {
                        String[] parts = line.split(" ");
                        if (parts.length > 2) {
                            String tableName = parts[2]; // 获取表名

                            // 生成删除外键约束和表的 SQL 语句
                            bw.write("BEGIN");
                            bw.newLine();
                            // 删除引用该表的外键约束
                            bw.write("    FOR rec IN (SELECT constraint_name, table_name FROM all_cons_columns WHERE table_name = '" + tableName + "' AND constraint_name IN (SELECT constraint_name FROM all_constraints WHERE constraint_type = 'R')) LOOP");
                            bw.newLine();
                            bw.write("        EXECUTE IMMEDIATE 'ALTER TABLE ' || rec.table_name || ' DROP CONSTRAINT ' || rec.constraint_name;");
                            bw.newLine();
                            bw.write("    END LOOP;");
                            bw.newLine();
                            bw.write("    EXECUTE IMMEDIATE 'DROP TABLE " + tableName + "';");
                            bw.newLine();
                            bw.write("EXCEPTION");
                            bw.newLine();
                            bw.write("    WHEN OTHERS THEN");
                            bw.newLine();
                            bw.write("        IF SQLCODE != -942 THEN -- 表不存在的错误代码");
                            bw.newLine();
                            bw.write("            RAISE;");
                            bw.newLine();
                            bw.write("        END IF;");
                            bw.newLine();
                            bw.write("END;");
                            bw.newLine();
                            bw.write("/"); // 结束 PL/SQL 语句块
                            bw.newLine();
                        }
                    }
                }

                // 判断是否包含 "alter"（忽略大小写），替换 "LYGZB_SOAR" 为 "PRHOUSE"
                if (line.toLowerCase().contains("alter".toLowerCase())) {
                    line = line.replaceAll("(?i)LYGZB_SOAR", "PRHOUSE");
                }

                // 如果包含 "tablespace"（示例，删除 prompt）
                if (line.contains("tablespace")) {
                    line = "";  // 删除包含 "tablespace" 的行
                }

                // 将处理后的行写入到新的文件
                bw.write(line);
                bw.newLine(); // 添加换行符
            }

            System.out.println("File processed successfully and saved as: " + outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
