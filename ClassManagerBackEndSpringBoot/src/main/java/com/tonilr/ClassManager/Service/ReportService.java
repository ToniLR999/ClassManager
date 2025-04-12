package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.Model.Class;
import com.tonilr.ClassManager.Model.Grade;
import com.tonilr.ClassManager.Repository.ClassRepository;
import com.tonilr.ClassManager.Repository.GradeRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

	@Autowired
    private final GradeRepository gradeRepository;
    
	@Autowired
	private final ClassRepository classRepository;

    public ReportService(GradeRepository gradeRepository, ClassRepository classRepository) {
		super();
		this.gradeRepository = gradeRepository;
		this.classRepository = classRepository;
	}

	public byte[] generateGradesReport(Long classId, String username) throws Exception {
        Class clazz = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!clazz.getProfessor().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized to generate report for this class");
        }

        List<Grade> grades = gradeRepository.findByClazzId(classId);

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Reporte de Calificaciones - Clase: " + clazz.getName(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell(new PdfPCell(new Phrase("Estudiante")));
        table.addCell(new PdfPCell(new Phrase("Nota")));
        table.addCell(new PdfPCell(new Phrase("Descripción")));
        table.addCell(new PdfPCell(new Phrase("Fecha")));

        for (Grade grade : grades) {
            table.addCell(grade.getStudent().getFullName());
            table.addCell(grade.getValue().toString());
            table.addCell(grade.getDescription() != null ? grade.getDescription() : "-");
            table.addCell(grade.getDate().toString());
        }

        document.add(table);
        document.close();

        return out.toByteArray();
    }
}