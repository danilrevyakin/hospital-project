package com.example.hospitalproject.userInfo.controller;

import com.example.hospitalproject.userInfo.model.Appointment;
import com.example.hospitalproject.userInfo.model.Doctor;
import com.example.hospitalproject.userInfo.model.Patient;
import com.example.hospitalproject.userInfo.model.UserInfo;
import com.example.hospitalproject.userInfo.repository.AppointmentRepository;
import com.example.hospitalproject.userInfo.repository.DoctorRepository;
import com.example.hospitalproject.userInfo.repository.PatientRepository;
import com.example.hospitalproject.userInfo.repository.UserInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/makeAppointment/{id}")
    public String makeAppointment(@PathVariable(value = "id") long id, Model model){
        UserInfo user = userInfoRepository.findById(id).orElseThrow();
        model.addAttribute("doctor", user);
        return "createAppointment";
    }

    @GetMapping("hospital/{docId}/appointment/{appId}/cancel")
    public String cancelAppointment(@PathVariable(value = "docId") long docId,
                                    @PathVariable(value = "appId") long appId, Model model){
        Optional<Appointment> appointmentResult = appointmentRepository.findById(appId);
        Appointment appointment = appointmentResult.orElseThrow();
        appointmentRepository.delete(appointment);
        return "redirect:/hospital/"+docId+"/appointments";
    }



    @PostMapping("/makeAppointment/{id}")
    public String sendRequestAppointment(@PathVariable(value = "id") long id, @RequestParam String day,
                                         @RequestParam String time, @RequestParam String description,
                                         @RequestParam(required = false) String offline, Model model, HttpSession session) throws ParseException {
        //check if present
        UserInfo user = userInfoRepository.findById((Long)session.getAttribute("id")).orElseThrow();
        UserInfo userInfo = userInfoRepository.findById(id).orElseThrow();
        List<Doctor> doctorList = doctorRepository.findByUserId(userInfo);

        //data validation
        Doctor doctor = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String start = time.split("-")[0];
        String end = time.split("-")[1];

        Date date = new Date(sdf.parse(day).getTime());
        Time startTime = Time.valueOf(start + ":00");
        Time endTime = Time.valueOf(end + ":00");
        Boolean offlineMode = offline != null;

        if(!doctorList.isEmpty())
            doctor = doctorList.get(0);


        Appointment appointment = createAppointment(date, startTime, endTime, offlineMode, description, doctor, user);
        List<Appointment> appointmentList = appointmentRepository.findByDateAndTimeAndDoctor(date, startTime, doctor);
        if(appointmentList.isEmpty()) {
            appointmentRepository.save(appointment);
            patientRepository.save(createPatient(userInfo, appointment));
            return "findDoctor";
        } else {
            model.addAttribute("id", id);
            return "timeSlotError";
        }

    }

    private Patient createPatient(UserInfo user, Appointment appointment) {
        Patient patient = new Patient();
        patient.setId(user.getId());
        patient.setUserId(user);
        patient.setAppointment(appointment);
        return patient;
    }

    private Appointment createAppointment(Date date, Time startTime, Time endTime, boolean offlineMode,
                                          String description, Doctor doctor, UserInfo userInfo) throws ParseException {


        Appointment appointment = new Appointment();
        appointment.setDay(date);
        appointment.setStart(startTime);
        appointment.setFinish(endTime);
        appointment.setDescription(description);
        appointment.setOffline(offlineMode);
        appointment.setDoctor(doctor);
        appointment.setUserInfo(userInfo);

        return appointment;
    }
}
