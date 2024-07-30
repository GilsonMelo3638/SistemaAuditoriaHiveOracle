package br.gov.df.economia.sistemaauditoriahiveoracle.application.Testes;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import br.gov.df.economia.sistemaauditoriahiveoracle.gui.util.Configuracao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinCSVFiles {

    public static void main(String[] args) {
        String agendaFilePath = Configuracao.DIRECTORY_PATH_PENDENCIA_HIVE + "Agenda.csv";
        String cargasHiveFilePath = Configuracao.DIRECTORY_PATH_PENDENCIA_HIVE + "CargasHive.csv";
        String outputFilePath = Configuracao.DIRECTORY_PATH_PENDENCIA_HIVE + "Resultado.csv";

        try {
            List<String[]> agendaRecords = readCSV(agendaFilePath);
            List<String[]> cargasHiveRecords = readCSV(cargasHiveFilePath);

            List<String[]> joinedRecords = performJoin(agendaRecords, cargasHiveRecords);

            writeCSV(outputFilePath, joinedRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readCSV(String filePath) throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            return csvReader.readAll();
        }
    }

    private static List<String[]> performJoin(List<String[]> agendaRecords, List<String[]> cargasHiveRecords) {
        List<String[]> joinedRecords = new ArrayList<>();

        for (String[] agendaRecord : agendaRecords) {
            String commonKeyAgenda = agendaRecord[0]; // Substitua pelo índice ou nome da coluna apropriado

            // Procurar correspondência com base na chave comum
            String[] correspondingCargasHiveRecord = cargasHiveRecords.stream()
                    .filter(record -> record[0].equals(commonKeyAgenda)) // Substitua pelo índice ou nome da coluna apropriado
                    .findFirst()
                    .orElse(null);

            // Se houver correspondência, adicione as colunas relevantes ao registro do resultado
            if (correspondingCargasHiveRecord != null) {
                List<String> joinedRecordList = new ArrayList<>(Arrays.asList(agendaRecord));
                joinedRecordList.addAll(Arrays.asList(correspondingCargasHiveRecord).subList(1, correspondingCargasHiveRecord.length));
                joinedRecords.add(joinedRecordList.toArray(new String[0]));
            } else {
                // Se não houver correspondência, apenas adicione o registro da agenda
                joinedRecords.add(agendaRecord);
            }
        }

        return joinedRecords;
    }

    private static void writeCSV(String filePath, List<String[]> records) throws IOException {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            csvWriter.writeAll(records);
        }
    }
}
