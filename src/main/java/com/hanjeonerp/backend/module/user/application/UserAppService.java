    package com.hanjeonerp.backend.module.user.application;

    import com.hanjeonerp.backend.core.exception.BadRequestException;
    import com.hanjeonerp.backend.module.user.api.dto.register.EngineerReq;
    import com.hanjeonerp.backend.module.user.api.dto.register.EngineerRes;
    import com.hanjeonerp.backend.module.user.api.dto.register.SalesmanReq;
    import com.hanjeonerp.backend.module.user.api.dto.register.SalesmanRes;
    import com.hanjeonerp.backend.module.user.api.dto.update.UpdateEngineerRes;
    import com.hanjeonerp.backend.module.user.api.dto.update.UpdateSalesmanReq;
    import com.hanjeonerp.backend.module.user.api.dto.update.UpdateSalesmanRes;
    import com.hanjeonerp.backend.module.user.domain.entity.User;
    import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
    import com.hanjeonerp.backend.module.user.domain.service.UserService;
    import com.hanjeonerp.backend.module.user.domain.vo.SalesmanProfile;
    import com.hanjeonerp.backend.module.user.domain.vo.UserBasicProfile;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    @RequiredArgsConstructor
    public class    UserAppService {
        private final UserService userService;
        private final UserRepo userRepo;
        private final PasswordEncoder passwordEncoder;

        // 영업사원 추가
        @Transactional
        public SalesmanRes signUp(SalesmanReq req) {
            //비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(req.getPassword());

            // 영업사원 프로필 생성
            UserBasicProfile basicProfile = req.toBasicProfile();
            SalesmanProfile profile = req.toSalesmanProfile();

            //영업사원 생성
            User user = userService.createSalesMan(req.getUsername(), encodedPassword, basicProfile, profile);

            // 영업사원 저장
            userRepo.save(user);

            // 응답 DTO 생성
            return SalesmanRes.from(user);
        }

        @Transactional
        public UpdateSalesmanRes updateSalesman(Long userId, UpdateSalesmanReq req) {
            // 수정 대상은 userId로 찾음
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new BadRequestException("수정할 유저가 존재하지 않습니다."));

            // username 변경
            if (req.getUsername() != null && !req.getUsername().equals(user.getUsername())) {
                if (userRepo.existsByUsername(req.getUsername())) {
                    throw new BadRequestException("이미 사용 중인 아이디입니다.");
                }
                user.changeUsername(req.getUsername());
            }

            // password 변경
            if (req.getPassword() != null) {
                user.changePassword(passwordEncoder.encode(req.getPassword()));
            }

            // profile 변경
            UserBasicProfile newBasic = req.toBasicProfile(user.getBasicProfile());
            SalesmanProfile newSalesman = req.toSalesmanProfile(user.getSalesmanProfile());
            user.changeSalesmaneProfile(newBasic, newSalesman);

            return UpdateSalesmanRes.from(user);
        }

        // 영업사원 삭제
        @Transactional
        public void deleteSalesman(Long userId) {
            // 삭제 대상은 userId로 찾음
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new BadRequestException("삭제할 유저가 존재하지 않습니다."));

            // 소프트 삭제
            user.delete();
            userRepo.save(user);
        }

        //엔지니어 추가
        public EngineerRes createEngineer(EngineerReq req) {
            //비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(req.getPassword());

            //엔지니어 프로필 생성
            UserBasicProfile engineerProfile =  req.toProfile();

            //엔지니어 생성
            User engineer = userService.createEngineer(req.getUsername(), encodedPassword, engineerProfile);


            //엔지니어 저장
            engineer = userRepo.save(engineer);

            //엔지니어 응답 DTO 생성
            return EngineerRes.from(engineer);
        }

        //엔지니어 수정
        public UpdateEngineerRes updateEngineer(Long userId, EngineerReq req) {
            //수정할 대상 찾기
            User engineer = userRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("수정할 엔지니어가 존재하지 않습니다."));

            //엔지니어 프로필 변경
            UserBasicProfile engineerProfile = req.toProfile();
            engineer.changeEngineerProfile(engineerProfile);

            //엔지니어 저장
            engineer = userRepo.save(engineer);

            //엔지니어 응답 DTO 생성
            return UpdateEngineerRes.from(engineer);
        }

        //엔지니어 삭제 soft delete
        public void deleteEngineer(Long userId) {
            User engineer = userRepo.findById(userId)
                    .orElseThrow(() -> new BadRequestException("삭제할 엔지니어가 존재하지 않습니다."));

            // 엔지니어 삭제
            engineer.delete();
            userRepo.save(engineer);
        }
    }




