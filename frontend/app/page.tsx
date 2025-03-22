import React, { ReactElement } from "react";
import Image from "next/image";
import Link from "next/link";
import styles from "./page.module.css";

export default function Home(): ReactElement {
  return (
    <div className="bg-white">
      {/* Phần Hero (Màn hình đầu tiên) */}
      <section className={`mr-2 ${styles.heroSection}`}>
        {/* Nội dung bên trái */}
        <div className={styles.heroContent}>
          <h1 className={styles.heroTitle}>Học những khái niệm mới mỗi phút</h1>
          <p className={styles.heroParagraph}>
            Chúng tôi giúp bạn chuẩn bị cho các kỳ thi và bài kiểm tra
          </p>
          <div className="flex space-x-4">
            {/* <button className={styles.heroButtonPrimary}>
              Bắt đầu làm bài
            </button> */}
            <Link href="/student/1" className={styles.heroButtonPrimary}>
              Bắt đầu làm bài
            </Link>
            <Link href="#know-more" className={styles.heroButtonSecondary}>
              Tìm hiểu thêm
            </Link>
          </div>
        </div>
        {/* Hình minh họa */}
        <div className={styles.heroImageWrapper}>
          <Image
            src="/assets/Illusttration.png"
            alt="Hình minh họa"
            width={600}
            height={600}
            priority
          />
        </div>
      </section>

      {/* Màn hình thứ hai (Tính năng) */}
      <section id="features" className={styles.featuresSection}>
        <h2 className={styles.featuresTitle}>
          Online Exam System có những{" "}
          <span className={styles.featuresHighlight}>tính năng tuyệt vời</span>{" "}
          như:
        </h2>
        <div className={styles.featuresContainer}>
          {/* Tính năng 1 */}
          <div className={styles.featureItem}>
            <h3 className={styles.featureItemTitle}>Phủ sóng 3D</h3>
            <p className={styles.featureItemText}>
              Bao phủ 3 chiều cho tất cả các câu hỏi liên quan đến một chủ đề cụ
              thể.
            </p>
          </div>
          {/* Tính năng 2 */}
          <div className={styles.featureItem}>
            <h3 className={styles.featureItemTitle}>Nhiều môn học</h3>
            <p className={styles.featureItemText}>
              Nhiều môn học để lựa chọn, ví dụ: Ngôn ngữ lập trình, các môn kỹ
              thuật, v.v.
            </p>
          </div>
          {/* Tính năng 3 */}
          <div className={styles.featureItem}>
            <h3 className={styles.featureItemTitle}>Giải pháp chi tiết</h3>
            <p className={styles.featureItemText}>
              Giải thích chi tiết về một giải pháp được cung cấp để hiểu sâu hơn
              về một chủ đề.
            </p>
          </div>
        </div>
      </section>

      {/* Màn hình thứ ba (Về chúng tôi) */}
      <section id="about-us" className={styles.aboutUsSection}>
        <h2 className={styles.aboutUsTitle}>Về chúng tôi</h2>
        <div className={styles.aboutUsText}>
          <p>
            Online Exam System là nền tảng được xây dựng bởi các nhà giáo dục và
            sinh viên tin vào sức mạnh của việc học theo từng phần nhỏ. Sứ mệnh
            của chúng tôi là làm cho việc học trở nên thú vị, dễ tiếp cận và
            liên tục—mỗi phút trong ngày.
          </p>
          <p>
            Chúng tôi không ngừng cập nhật các bài kiểm tra và tài liệu học tập
            để đảm bảo chúng luôn phù hợp và hiệu quả. Hãy tham gia cùng chúng
            tôi và trải nghiệm một cách học tập mới!
          </p>
        </div>
      </section>

      {/* Màn hình thứ tư (Hành trình học tập) */}
      <section id="learning-journey" className={styles.learningJourneySection}>
        <h2 className={styles.learningJourneyTitle}>
          Hãy khám phá{" "}
          <span className={styles.learningJourneyHighlight}>
            hành trình học tập
          </span>{" "}
          của bạn
        </h2>
        <div className={styles.learningJourneyGrid}>
          {/* Bước 1 */}
          <div className={styles.learningJourneyStep}>
            <div className={styles.learningJourneyIconWrapper}>
              <Image
                src="/assets/Group 26.png"
                alt="Biểu tượng 1"
                width={40}
                height={40}
              />
            </div>
            <div>
              <h3 className={styles.learningJourneyStepTitle}>
                1. Chọn môn học của bạn
              </h3>
              <p className={styles.learningJourneyStepText}>
                Chọn môn học yêu thích từ danh sách đa dạng các môn học và tiếp
                tục hành trình của bạn.
              </p>
            </div>
          </div>
          {/* Bước 2 */}
          <div className={styles.learningJourneyStep}>
            <div className={styles.learningJourneyIconWrapper}>
              <Image
                src="/assets/Group 27.png"
                alt="Biểu tượng 2"
                width={40}
                height={40}
              />
            </div>
            <div>
              <h3 className={styles.learningJourneyStepTitle}>
                2. Chọn độ khó
              </h3>
              <p className={styles.learningJourneyStepText}>
                Chọn độ khó bạn mong muốn và nhận các câu hỏi phù hợp.
              </p>
            </div>
          </div>
          {/* Bước 3 */}
          <div className={styles.learningJourneyStep}>
            <div className={styles.learningJourneyIconWrapper}>
              <Image
                src="/assets/Group 28.png"
                alt="Biểu tượng 3"
                width={40}
                height={40}
              />
            </div>
            <div>
              <h3 className={styles.learningJourneyStepTitle}>
                3. Độ khó tăng dần
              </h3>
              <p className={styles.learningJourneyStepText}>
                Độ khó của các câu hỏi tiếp theo sẽ tăng dần bất kể câu trả lời
                trước của bạn.
              </p>
            </div>
          </div>
          {/* Bước 4 */}
          <div className={styles.learningJourneyStep}>
            <div className={styles.learningJourneyIconWrapper}>
              <Image
                src="/assets/Group 29.png"
                alt="Biểu tượng 4"
                width={40}
                height={40}
              />
            </div>
            <div>
              <h3 className={styles.learningJourneyStepTitle}>
                4. Tổng quan chi tiết điểm số
              </h3>
              <p className={styles.learningJourneyStepText}>
                Nhận tổng quan chi tiết về phiên làm bài của bạn và các mẹo để
                cải thiện lần sau.
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
