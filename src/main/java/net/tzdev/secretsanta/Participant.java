package net.tzdev.secretsanta;

/**
 * Secret santa participant model.
 *
 * @author ahelac
 */
public class Participant {

  private String email_;
  private String name_;

  public Participant() {
  }

  public Participant(String email, String name) {
    email_ = email;
    name_ = name;
  }

  @Override
  public String toString() {
    return name_;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Participant that = (Participant) o;

    return email_.equals(that.email_);

  }

  @Override
  public int hashCode() {
    return email_.hashCode();
  }

  public String getEmail() {
    return email_;
  }

  public void setEmail(String email) {
    email_ = email;
  }

  public String getName() {
    return name_;
  }

  public void setName(String name) {
    name_ = name;
  }
}
